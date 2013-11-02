package asu.edu.sbs.web.it;

import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




//import asu.edu.sbs.domain.IBankRoles;
import asu.edu.sbs.domain.SignUpEmployee;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.email.EmailNotificationManager;
import asu.edu.sbs.exception.BankAccessException;
import asu.edu.sbs.exception.BankStorageException;
import asu.edu.sbs.it.service.ItEmployee;
import asu.edu.sbs.login.service.OneTimePassword;


@Controller
public class ItEmployeeController {

	@Autowired
	private ItEmployee itEmployee;
	@Autowired
	private EmailNotificationManager enManager;
	
	@RequestMapping(value = "/it/employee", method = RequestMethod.GET)
	public String regularEmp(Locale locale, Model model, Principal principal) {
		System.out.println("Inside IT employee controller.............");
		String name = principal.getName();
		model.addAttribute("username", name);
		return "it/employee/employee";
	}
	@RequestMapping(value = "it/op1", method = RequestMethod.POST)
	public String getPendingRequests(Locale locale, Model model, Principal principal) {
		System.out.println("Inside employee Controller for iiit.............");
		
		List<User> userRequests = itEmployee.getAllPendingUserRequests();
		//System.out.println(userRequests.get(0).getFirstName());
		String name = principal.getName();
		model.addAttribute("username", name);
		String message = "";
		model.addAttribute("Message", message);
		model.addAttribute("userRequests", userRequests);
		return "it/employee/ItApprovePendingRequests";
	}

	@RequestMapping(value = "it/op2", method = RequestMethod.POST)
	public String postUserRequests(Locale locale, Model model, Principal principal) {
		System.out.println("Inside employee Controller for it.............");
		String name = principal.getName();
		model.addAttribute("username", name);
		return "it/employee/deleteCustomer";
	}
	
	@RequestMapping(value = "it/employee/deleteUser", method = RequestMethod.POST)
	public String postDeleteUserRequests(Locale locale, Model model, HttpServletRequest request, Principal principal) throws BankStorageException, BankAccessException
	{
		System.out.println("Inside employee Controller for it.............");
		String name = principal.getName();
		model.addAttribute("username", name);
		try {
			itEmployee.deleteUser(request.getParameter("userNametext"), name);
		} catch (BankStorageException e) {
			// TODO Auto-generated catch block
			throw new BankStorageException(e);
		} catch (BankAccessException e) {
			// TODO Auto-generated catch block
			throw new BankAccessException(e);
		}
;		return "it/employee/employee";
	}
	
	@RequestMapping(value = "it/handlePendingRequestsResponse.html", method = RequestMethod.POST)
	public String pendingUserRequests(Locale locale, Model model, HttpServletRequest request, Principal principal) throws BankAccessException, BankStorageException {
	
		System.out.println("Inside employee Controller for it.............");
		String name = principal.getName();
		String message = null;
		model.addAttribute("username", name);
		//ToDo: Handle This case
		if(request.getParameterValues("selected")==null)
		{
			List<User> userRequests = itEmployee.getAllPendingUserRequests();
			System.out.println(userRequests.get(0).getFirstName());
			model.addAttribute("username", name);
			message = "Error:No Option Selected.Please select atleast one option!.Press Home to return to main page";
			model.addAttribute("message", message);
			model.addAttribute("userRequests", userRequests);
			return "it/employee/resultItPendingUser";
		}
		if(request.getParameter("action")==null)
		{
			//ToDo:ToHandle 
			throw new BankAccessException("Oops!! You seem to be lost because of some Bad Operation. Please press the Home button to return to your mainpage or Logout.");
		}
		if(request.getParameter("action").equals("approve"))
		{
			//ToDo:Make one DB call for all the selected requests
			for(String username: request.getParameterValues("selected"))
			{
				
				// Delete from the tbl_it_pending
			
					User user = null;
					try {
						if(username==null)
							throw new BankAccessException();
						
						user = itEmployee.getPendingUserRequest(username);
						OneTimePassword otpInstance = new OneTimePassword();
						String password = otpInstance.getPassword();
						Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				    	String hashedPass = encoder.encodePassword(password, null);
				    	// Insert into customers table
/*				    	System.out.println("The user who will be added is :" + user.getUsername());*/
						user.setRole(user.getDepartment());
						try{
							itEmployee.deleteItPendingRequest(username);
						}
						catch(Exception e)
						{
							String errorMsg = e.getMessage();
							System.out.println(errorMsg);					
							if(errorMsg!=null && errorMsg.equals("No such user exists. This action has been logged. Please don't try to hack into the system !!!"))
							{
								//ToDo: Shardul Should this be BankAccess?!
								throw new BankAccessException("No such user exists!!.The request might have been processesed by another IT employee.  Please press the Home button to return to your mainpage or Logout.");
							}
							throw new BankAccessException();
							
						}
/*						System.out.println("The user who will be added is :" + user.getUsername());*/
						itEmployee.insertValidUser(user, password, name);
/*						System.out.println("The user who will be added is :" + user.getUsername());*/
						long min = 1l;
						long max = 999999999l;
						Long random = max + (long)(Math.random()*((max - min) + 1));	
						String accountNo = random.toString();
						//Shardul Critical ToDo: Integrate initial amount in form or add Debit/Credit , setting initial amount to 250
						//Shardul Critical ToDo: Check if accountNo collision will occur and handle if required
						itEmployee.insertCustomerAccNo(user.getUsername(), accountNo, 500.0, name);
						//send email
						enManager.sendPasswordCustomer(user, otpInstance.getPassword());
						// ToDo: Shardul On basis of above output
					}
					catch (Exception e) {
						String errorMsg = e.getMessage();
						System.out.println(errorMsg);
						// ToDo: Inconsistent state shall be maintained if one insert fails.
						// ToDo: Shardul Note:Insert Acc, no must not ideally throw this message
						if(errorMsg!=null)
						{
							e.printStackTrace();
							try {
								itEmployee.saveNewEmployeeRequest(user, name);
							 } catch (Exception e1) {
								 throw new BankAccessException(e1);
							 }
								//ToDo: Shardul Should this be BankAccess?!
							throw new BankStorageException();
						}
						throw new BankAccessException();
					}
					
			}
			message = "The selected customers were added to the system. Temporary password is sent to the email provided by the user";
			model.addAttribute("message", message);
			return "it/employee/resultItPendingUser";
/*			return "it/employee/requestsApproved";*/
		}
		if(request.getParameter("action").equals("deny"))
		{
			for(String username:request.getParameterValues("selected"))
			{
				// Delete from the tbl_it_pending
				try {
					itEmployee.deleteItPendingRequest(username);
				} catch (Exception e) {

					String errorMsg = e.getMessage();
					System.out.println(errorMsg);					
					if(errorMsg!=null && errorMsg.equals("No such user exists. This action has been logged. Please don't try to hack into the system !!!"))
					{
						throw new BankAccessException("No such user exists!!.The request might have been processesed by another IT employee.  Please press the Home button to return to your mainpage or Logout.");
					}
					e.printStackTrace();
				}
				// Optional Insert into customers_deny table table
				
			}
			message = "The requests of selected customers was denied! Please press the Home button to return to your mainpage or Logout";
			model.addAttribute("message", message);
			return "it/employee/resultItPendingUser";
			
		}	
		return "it/op1";
	}
	

}
