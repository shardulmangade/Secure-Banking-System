package asu.edu.sbs.web.corporatemanager;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.activity.InvalidActivityException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import asu.edu.sbs.hr.service.CorporateManager;
import asu.edu.sbs.web.login.LoginController;

@Controller
//@RequestMapping(value= "/corporateDept")
public class CorporateController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
		
	@Autowired
	CorporateManager crManager;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/corporate", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome to corporate page, locale is {}.", locale);
		
		return "corporate/corporate";
	}
	
	
	@RequestMapping(value="/corporate/op2",  method = RequestMethod.POST)
	public String getTest2(Locale locale, Model model) {
		logger.info("Welcome to corporate page, locale is {}.", locale);
		
		return "corporate/delete";
	}
	
	@RequestMapping(value="/corporate/op3",  method = RequestMethod.POST)
	public ModelAndView getTest3(Locale locale, Model model) {
		
		Map <String,String> department = new LinkedHashMap<String,String>();			
		department.put("sales", "Sales department");
		department.put("TM", "Transaction Management department");
		department.put("IT", "IT & Tech Support department");
		department.put("CM", "Company Managment department");
		model.addAttribute("departmentList", department);		
		return new ModelAndView("corporate/transfer", "signupemployee", new SignUpEmployee());
		
	}
	
	@RequestMapping(value = "corporate/op4" ,method = RequestMethod.POST)
	public String getPending(Locale locale, Model model){
		
		System.out.println("Inside corporate controler for pending request");
		
		List<SignUpEmployee> singupList = crManager.getAllPendingUserRequests();
//		/System.out.println(userRequests.get(0).getFirstName());
		model.addAttribute("userRequests", singupList);		
		return "corporate/pending";
	}
	
	@RequestMapping(value = "/corporate/op1" ,method = RequestMethod.POST)
	public ModelAndView getDataEmployee(Locale locale , Model model)
	{
		System.out.println("\n Inside Employee signup controller");		
		
		Map <String,String> department = new LinkedHashMap<String,String>();
		department.put("HR", "HR department");
		department.put("sales", "Sales department");
		department.put("TM", "Transaction Management department");
		department.put("IT", "IT & Tech Support department");
		department.put("CM", "Company Managment department");
		model.addAttribute("departmentList", department);
		return new ModelAndView("corporate/add", "signupuser", new SignUpEmployee());
	}	
	
	@RequestMapping(value = "/corporate/corporateadduser" ,method = RequestMethod.POST)
	public ModelAndView postDataEmployee(@ModelAttribute @Valid SignUpEmployee employee, BindingResult result, final RedirectAttributes attributes)
	 {
		String message ;
		ModelAndView mav = new ModelAndView();
		try{				
			System.out.println("\n Inside Employee signup post controller");
			if(result.hasErrors())
			{
				return new ModelAndView("corporate/add", "signupuser",employee);
			}		 
					
			mav.setViewName("signup/saveData"); // need getter methods for setView. Using saveData in signup.
			message= "Your request has been submitted for approval";
			crManager.saveNewEmployeeRequest(employee);
			mav.addObject("message", message);				
			return mav;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e instanceof com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException )
			{
				message = "Username already Exists.Choose a different username";
				mav.addObject("message", message);
				mav.setViewName("signup/saveData");		
				return mav;
			} else
			{
				message = "Error in saving your data.Please try again";
				mav.addObject("message", message);
				mav.setViewName("signup/saveData");		
				return mav;
					
			}
		 }	
	 }	
	
	@RequestMapping(value = "/corporate/corporatedelete" ,method = RequestMethod.POST)
	public String deleteEmployeePost(Model model,HttpServletRequest request)
	{
		System.out.println("\n Inside corporate delete empployee post controller");
		String message = null, userName;
		userName = request.getParameter("userNametext");
		try{
			crManager.deleteEmployee(userName);
			message = "User deleted successfully";
			model.addAttribute("message", message);							
			return ("corporate/saveData");			
		}catch(Exception e){
			if(e instanceof InvalidActivityException )
			{
				e.printStackTrace();		
				message = "Error occured in deleting employee .Please use valid username";
				model.addAttribute("message", message);							
				return ("corporate/saveData");
			} else {
				// TODO Auto-generated catch block
				e.printStackTrace();						
				message = "Error occured in sending delete request";
				model.addAttribute("message", message);				
				return ("corporate/saveData");
			}
		}
	}
	
	@RequestMapping(value = "/corporate/corporateUpdate" ,method = RequestMethod.POST)
	public String transferUserCorporate(SignUpEmployee employee,Model model,HttpServletRequest request)
	{
		System.out.println("\n Inside corporate delete empployee post controller");
		String message,department = null ;
								
		try{												
			message= "Employee "+ request.getParameter("userNametext")+ " has been transfered";					
			crManager.updateDepartmentOfEmployee(request.getParameter("userNametext"), employee.getDepartment());
			model.addAttribute("message", message);							
			return ("signup/saveData");
			
		} catch (Exception e) {
			
			if(e instanceof InvalidActivityException )
			{
				e.printStackTrace();		
				message = "Error occured in transferring employee .Please use valid username";
				model.addAttribute("message", message);							
				return ("signup/saveData");
			} else {
			// TODO Auto-generated catch block
			e.printStackTrace();						
			message = "Error occured in sending transfer request";
			model.addAttribute("message", message);				
			return ("signup/saveData");
			}
		 }		
	}

	@RequestMapping(value = "/corporate/pending", method = RequestMethod.POST)
	public String pendingResponse(Locale locale, Model model, HttpServletRequest request) {
		System.out.println("Inside handle pending request of the corporate user");
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
				try {
					crManager.deleteEmployee(username);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Delete from both tables
			}
			return "/corporate/corporate";
		}
		if(request.getParameter("action").equals("deny"))
		{
			for(String username:request.getParameterValues("selected"))
			{
				//delete from one table 
				try {
					crManager.deleteEmployeeRequest(username);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return "/corporate/corporate";
			
		}	
		return "corporate/corporate";
	}

}

