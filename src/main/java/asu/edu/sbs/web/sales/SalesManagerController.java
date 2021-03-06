package asu.edu.sbs.web.sales;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.activity.InvalidActivityException;
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

import asu.edu.sbs.domain.IBankRoles;
import asu.edu.sbs.domain.SignUpExternalEmployee;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.email.EmailNotificationManager;
import asu.edu.sbs.exception.BankAccessException;
import asu.edu.sbs.exception.BankDeactivatedException;
import asu.edu.sbs.login.service.OneTimePassword;
import asu.edu.sbs.sales.service.SalesDeptManager;

@Controller
@RequestMapping(value= "/sales/salesmanager")

public class SalesManagerController {

	@Autowired
	SalesDeptManager salesmanager;
	ModelAndView savedMav;
	ModelAndView savedMav1;
	@Autowired
	private EmailNotificationManager enManager;
		
		@RequestMapping(value = "/manager", method = RequestMethod.GET)
		public String addnewSalesEmployee(Locale locale, Model model,Principal principal)throws BankDeactivatedException {
			System.out.println("Inside Sales manager Controller .............");
			model.addAttribute("username", principal.getName());
			return "sales/manager/manager";
		} 
		
		@RequestMapping(value = "sales/salesmanager", method = RequestMethod.POST)
		public String addnewsalesEmployeePost(Locale locale, Model model,Principal principal) throws BankDeactivatedException{
			System.out.println("Inside sales manager post Controller .............");				
			model.addAttribute("username", principal.getName());	
			return "sales/manager/salesmanager";
		}
		
		
		@RequestMapping(value = "/newsalesemployee", method = RequestMethod.POST)
		public ModelAndView newSalesEmployeeGet(Locale locale, Model model, Principal principal) throws BankDeactivatedException {
			System.out.println("Inside Sales manager get Controller .............");
//			model.addAttribute("username", principal.getName());
			savedMav = new ModelAndView("sales/newsalesemployee", "signupemployee", new User());
			
			return savedMav;
		}
		
		@RequestMapping(value = "/newsalesexternalemployee", method = RequestMethod.POST)
		public ModelAndView newSalesExternalEmployeeGet(Locale locale, Model model, Principal principal) throws BankDeactivatedException {
			System.out.println("Inside Sales employee get Controller .............");					
			model.addAttribute("username", principal.getName());
			savedMav1 = new ModelAndView("sales/newsalesexternalemployee", "signupexternalemployee", new SignUpExternalEmployee());
			
			return savedMav1;
		}
		
		@RequestMapping(value = "/newsalesemployee/op1", method = RequestMethod.POST)
		public ModelAndView newSalesEmployeePost(@ModelAttribute @Valid User user, BindingResult result, final RedirectAttributes attributes, Principal principal) throws BankDeactivatedException {
			System.out.println("INSIDE Sales manager post Controller .............");
			OneTimePassword otp = new OneTimePassword() ;
			String message ;
			ModelAndView mav = new ModelAndView();
			mav.getModelMap().addAttribute("username", principal.getName());
			try{				
				System.out.println("\n Inside Employee signup post controller");
				if(result.hasErrors())
				{
					message = "**ERRORS** observed in validating.Please go back and enter valid information";
					mav.addObject("message", message);
					mav.setViewName("signup/saveData");
					return mav;
					//return new ModelAndView("sales/manager/manager","signupemployee",user);
				}		 
						
				mav.setViewName("signup/saveData");
				message= "Congratulations..!! Your request has been approved. Please ask the user to check the email and login";
				user.setDepartment("sales");
				user.setRole(IBankRoles.ROLE_SALES_EMPLOYEE);
				user.setCreatedBy(principal.getName());				
				Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
				String password = otp.getPassword();
				String hashedPassword = passwordEncoder.encodePassword(otp.getPassword(), null);
				salesmanager.insertValidUser(user, hashedPassword, principal.getName());
				//salesmanager.insertValidCustomer(user,principal.getName());
				enManager.sendPassword(user, password);
				mav.addObject("message", message);								
				mav.addObject("username", principal.getName());
				//user.setPassword("temppassword");
				return mav;
			}
		 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e instanceof com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException )
			{
				message = "Username already Exists.Choose a different username";
				mav.addObject("message", message);
				mav.setViewName("signup/saveData");
				mav.addObject("username",principal.getName() );
				return mav;
			} 
			else if(e instanceof BankDeactivatedException)
			{
				throw new BankDeactivatedException(e.getMessage());
			}
			else
			{
				message = "Error in saving your data.Please try again";
				mav.addObject("message", message);
				mav.setViewName("signup/saveData");
				mav.addObject("username",principal.getName() );
				return mav;					
			}
		  } 
		}
		
		@RequestMapping(value = "/deletesalesemployee/op1" ,method = RequestMethod.POST)
		public String deleteEmployeeGet(Model model,HttpServletRequest request, Principal principal)throws BankDeactivatedException
		{
			model.addAttribute("username", principal.getName());
			return  ("sales/deletesalesemployee");
		}
			
		
		@RequestMapping(value = "/deletesalesemployee" ,method = RequestMethod.POST)
		public String deleteEmployeePost(Model model,HttpServletRequest request, Principal principal) throws BankDeactivatedException
		{
			System.out.println("\n Inside delete employee post controller");
			String message = null ,userName;
			userName = request.getParameter("userNametext");
			model.addAttribute("username", principal.getName());
			int status;
			
			try{	
				if(userName.equals(""))
				{
					throw new BankAccessException("Username is not valid. please enter a valid user");
				}
				User user = salesmanager.getUser(userName);
				if(!user.getDepartment().equals("sales"))
					throw new BankAccessException("The employee may not belong to this Department!!");
				status = salesmanager.getDeleteApprovalStatus(userName, "Sales");
				if(status==1 )
				{					
					message = "Employee "+ userName+ " has been deleted after approval of corporate level manager";
					salesmanager.deleteSalesEmployee(userName);					
				} else  if (status==0){
					message= "Employee "+ userName+ " delete request has not beeen approved by corporate level manager yet";											
				} else if (status==-1)
				{
					message= "Employee "+ userName+ " delete request has beeen sent for approval to corporate level manager";						
					salesmanager.insertDeleteRequesttoCM(userName,"Sales",false);
				}
				model.addAttribute("message", message);	
				model.addAttribute("username", principal.getName());
				return ("signup/saveData");
				
			} catch (Exception e) {
				if(e instanceof InvalidActivityException )
				{
					e.printStackTrace();		
					message = "Error occured in deleting employee .Please use valid username";
					model.addAttribute("message", message);		
					model.addAttribute("username", principal.getName());
					return ("signup/saveData");
				} 
				else if(e instanceof BankDeactivatedException)
				{
					throw new BankDeactivatedException(e.getMessage());
				}else {
				// TODO Auto-generated catch block
				e.printStackTrace();						
				message = "Error occured in sending delete request";
				model.addAttribute("message", message);		
				model.addAttribute("username", principal.getName());
				return ("signup/saveData");
				}
			 }		
		}
		
		
		@RequestMapping(value = "/transfersalesemployee" ,method = RequestMethod.POST)
		public ModelAndView transferEmployeeGet(Model model,HttpServletRequest request, Principal principal)throws BankDeactivatedException
		{								
			Map <String,String> department = new LinkedHashMap<String,String>();			
			department.put("HR", "HR department");
			department.put("TM", "Transaction Management department");
			department.put("IT", "IT & Tech Support department");
			department.put("CM", "Company Managment department");
			model.addAttribute("departmentList", department);		
			model.addAttribute("username", principal.getName());
			
			Map <String,String> roleList = new LinkedHashMap<String,String>();			
			roleList.put("manager", "manager");
			roleList.put("employee", "employee");
			model.addAttribute("roleList", roleList);	
			
			return new ModelAndView("sales/transfersalesemployee", "signupemployee", new User());
		}
		
		@RequestMapping(value = "/transfersalesemployee/op1" ,method = RequestMethod.POST)
		public String transferSalesEmployee(User user,Model model,HttpServletRequest request, Principal principal) throws BankDeactivatedException
		{
			System.out.println("\n Inside delete empployee post controller");
			String message,department = null ,username = null;
			String roleToBeupdated =null;
			username=request.getParameter("userNametext");
									
			try{												
				message= "Employee "+ username+ " has been transfered";
				if(user.getDepartment().equals("NONE") || user.getRole().equals("NONE"))
				{
					message="Oops!! You seem to be lost because of some Bad Operation. Please press the Home button to return to your mainpage or Logout.";
					model.addAttribute("message", message);
					model.addAttribute("username", principal.getName());
					return ("it/manager/saveData");	
				}
				roleToBeupdated = salesmanager.getRoleTobechanged(user.getDepartment(),user.getRole());
				salesmanager.updateUserRole(roleToBeupdated,"sales",user.getDepartment(),username ,principal.getName());
				model.addAttribute("message", message);
				model.addAttribute("username", principal.getName());
				return ("signup/saveData");	
				
				} catch (Exception e) {
					
				if(e instanceof InvalidActivityException )
				{
					e.printStackTrace();		
					message = "Error occured in transferring employee .Please use valid username";
					model.addAttribute("message", message);	
					model.addAttribute("username", principal.getName());
					return ("signup/saveData");
				} 
				else if(e instanceof BankDeactivatedException)
				{
					throw new BankDeactivatedException(e.getMessage());
				}else {
				// TODO Auto-generated catch block
				e.printStackTrace();						
				message = "Error occured in sending transfer request";
				model.addAttribute("message", message);		
				model.addAttribute("username", principal.getName());
				return ("signup/saveData");
				}
			 }		
		}
	
}
