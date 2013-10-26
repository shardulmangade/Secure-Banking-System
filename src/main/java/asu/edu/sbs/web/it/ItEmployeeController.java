package asu.edu.sbs.web.it;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import asu.edu.sbs.domain.SignUpEmployee;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.it.service.ItEmployee;


@Controller
public class ItEmployeeController {

	@Autowired
	private ItEmployee itEmployee;
	
	@RequestMapping(value = "/it/employee", method = RequestMethod.GET)
	public String regularEmp(Locale locale, Model model) {
		System.out.println("Inside IT employee controller.............");
		
		return "it/employee/employee";
	}
	@RequestMapping(value = "it/op1", method = RequestMethod.POST)
	public String getPendingRequests(Locale locale, Model model) {
		System.out.println("Inside employee Controller for iiit.............");
		
		List<User> userRequests = itEmployee.getAllPendingUserRequests();
		System.out.println(userRequests.get(0).getFirstName());
		model.addAttribute("userRequests", userRequests);
		return "it/employee/ItApprovePendingRequests";
	}

	@RequestMapping(value = "it/op2", method = RequestMethod.POST)
	public String postUserRequests(Locale locale, Model model) {
		System.out.println("Inside employee Controller for it.............");
		return "it/employee/employee";
	}
	
	@RequestMapping(value = "it/handlePendingRequestsResponse.html", method = RequestMethod.POST)
	public String pendingUserRequests(Locale locale, Model model, HttpServletRequest request) {
	
		System.out.println("Inside employee Controller for it.............");
		if(request.getParameterValues("selected")==null)
		{
			
			
		}
		if(request.getParameter("action")==null)
		{
		}
		System.out.println(request.getParameter("action"));
		if(request.getParameter("action").equals("approve"))
		{
			//ToDo:Make one DB call for all the selected requests
			for(String username: request.getParameterValues("selected"))
			{
				System.out.println(username);
				
				// Delete from the tbl_it_pending
/*				try {
					itEmployee.deleteEmployeeRequest(username);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				// Insert into customers table
			}
			return "it/employee/requestsApproved";
		}
		if(request.getParameter("action").equals("deny"))
		{
			for(String username:request.getParameterValues("selected"))
			{
				// Delete from the tbl_it_pending
/*				try {
					itEmployee.deleteEmployeeRequest(username);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				// Optional Insert into customers_deny table table
				
			}
			return "it/employee/requestsDenied";
			
		}	
		return "it/Trial";
	}
	
/*	@RequestMapping(value = "it/Trial" ,method = RequestMethod.POST)
	public ModelAndView postDataEmployee(@ModelAttribute @Valid SignUpEmployee employee, BindingResult result, final RedirectAttributes attributes)
	 {
		String message ;
		ModelAndView mav = new ModelAndView();
		System.out.println("Inside employee Controller for it.............");
//		try{				
//			System.out.println("\n Inside Employee signup post controller");
//			if(result.hasErrors())
//			{
//				return new ModelAndView("signup/signupemployee", "signupuser",employee);
//				//return new ModelAndView("hr/employee/hrEmployee","signupuser",employee);
//			}		 
//					
//			mav.setViewName("signup/saveData");
			message= "Your request has been submitted for approval";
//			hrmanager.saveNewEmployeeRequest(employee.getUserName(),employee.getFirstName(),employee.getLastName(),employee.getEmailId(),employee.getDepartment());
			mav.addObject("message", message);				
			return mav;
//		}
//	 catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//		if(e instanceof com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException )
//		{
//			message = "Username already Exists.Choose a different username";
//			mav.addObject("message", message);
//			mav.setViewName("signup/saveData");		
//			return mav;
//		} else
//		{
//			message = "Error in saving your data.Please try again";
//			mav.addObject("message", message);
//			mav.setViewName("signup/saveData");		
//			return mav;
//				
//		}
//	 }
				
	}*/

}
