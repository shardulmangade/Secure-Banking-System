package asu.edu.sbs.signup;

import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.activity.InvalidActivityException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import asu.edu.sbs.domain.PayrollEmployee;
import asu.edu.sbs.domain.SignUpEmployee;
//import asu.edu.sbs.domain.SignUpExternalEmployee;
import asu.edu.sbs.domain.SignUpUser;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.exception.BankDeactivatedException;
import asu.edu.sbs.hr.service.HrDeptManager;

@Controller
@RequestMapping(value= "/hr/hremployee")
public class SignupController {

	@Autowired
	HrDeptManager hrmanager;
	
	
	@RequestMapping(value = "signup" ,method = RequestMethod.GET)
	public ModelAndView getData( )
	{
		System.out.println("\n Inside signup controller of external user");		
		return new ModelAndView("signup/signup", "signupuser", new SignUpEmployee());		
	}
	
	@RequestMapping(value = "/signupPost" ,method = RequestMethod.POST)
	public ModelAndView getDataPost(@Validated @ModelAttribute SignUpUser user, BindingResult result, final RedirectAttributes attributes) throws BankDeactivatedException
	{
		System.out.println("\n Inside signup post controller");
		if(result.hasErrors())
		{
			return new ModelAndView("signup/signup", "signupuser",user);
		}		 
		
		ModelAndView mav = new ModelAndView();
		String message = "Submitted for approval";
		mav.setViewName("saveData");
		
		attributes.addFlashAttribute("message", message);	
		return mav;
				
	}

	
	@RequestMapping(value = "/signupemployee/op1" ,method = RequestMethod.POST)
	public ModelAndView getDataEmployee(Locale locale , Model model) throws BankDeactivatedException
	{
		System.out.println("\n Inside Employee signup controller");		
		
		Map <String,String> department = new LinkedHashMap<String,String>();
		department.put("HR", "HR department");
		department.put("sales", "Sales department");
		department.put("TM", "Transaction Management department");
		department.put("IT", "IT & Tech Support department");
		department.put("CM", "Company Managment department");
		model.addAttribute("departmentList", department);
		return new ModelAndView("signup/signup", "signupuser", new PayrollEmployee());
	}
	
//	@RequestMapping(value = "/hr/employee/hrEmployee", method = RequestMethod.GET)
//	public String addnewHrEmployeePost(Locale locale, Model model) {
//						
//		return "hr/employee/hrEmployee";
//	}
	
	@RequestMapping(value = "/SignupEmployeePost" ,method = RequestMethod.POST)
	public ModelAndView postDataEmployee(@ModelAttribute @Valid PayrollEmployee employee, BindingResult result, final RedirectAttributes attributes,Principal principal) throws BankDeactivatedException
	 {
		String message ;
		ModelAndView mav = new ModelAndView();
		try{				
			System.out.println("\n Inside Employee signup post controller");
			if(result.hasErrors())
			{
				message= "Please fill form properly, validation erros observed";
				mav.addObject("message", message);	
				mav.addObject("username", principal.getName());
				mav.setViewName("signup/saveData");
				return mav;
				//return new ModelAndView("hr/employee/hrEmployee","signupuser",employee);
			}		 
					
			mav.setViewName("signup/saveData");
			message= "Employee has been added to Payroll";
			User user = hrmanager.getUser(employee.getUserName());
			
			hrmanager.saveNewEmployeeRequest(user.getUsername(),user.getFirstName(),user.getLastName(),user.getEmail(),user.getDepartment());
			
			mav.addObject("message", message);				
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
			return mav;
		} else if(e instanceof BankDeactivatedException)
		{
			throw new BankDeactivatedException(e.getMessage());
		}else
		{
			message = "Error in saving your data.Please try again";
			mav.addObject("message", message);
			mav.setViewName("signup/saveData");		
			return mav;
				
		}
	 }
				
	}
	
		
	@RequestMapping(value = "/saveData" ,method = RequestMethod.POST)
	public String saveData(Locale locale , Model model) throws BankDeactivatedException
	{
		System.out.println("\n Inside savedata post controller");
		return "signup/saveData";
	}
	
	@RequestMapping(value = "/saveData/op1" ,method = RequestMethod.POST)
	public String saveDataPost(Locale locale , Model model) throws BankDeactivatedException
	{
		System.out.println("\n Inside savedata Get controller");
		return "signup/saveData";
	}
	
//	@RequestMapping(value = "/deleteemployee" ,method = RequestMethod.GET)
//	public ModelAndView deleteEmployee()
//	{
//		System.out.println("\n Inside delete empployee Get controller");
//		return new ModelAndView("signup/deleteEmployee", "deleteemployee", new SignUpUser());
//		
//	}  
	
	
	
	@RequestMapping(value = "/deleteemployee/op1" ,method = RequestMethod.POST)
	public String deleteEmployee(Model model) throws BankDeactivatedException
	{
		System.out.println("\n Inside delete empployee Get controller");
		//model.addAttribute("employeeObj", new SignUpEmployee());
		return ("signup/deleteEmployee");		
	}  

	@RequestMapping(value = "/deleteemployee" ,method = RequestMethod.POST)
	public String deleteEmployeePost(Model model,HttpServletRequest request) throws BankDeactivatedException
	{
		System.out.println("\n Inside delete empployee post controller");
		String message ;
								
		try{												
			message= "Employee has been removed from payroll";
			System.out.println("request is :"+request.getParameter("userNametext"));			
			hrmanager.deleteEmployeeRequest(request.getParameter("userNametext"));
			model.addAttribute("message", message);							
			return ("signup/saveData");
			
		} catch (Exception e) {
			if(e instanceof InvalidActivityException )
			{
				e.printStackTrace();		
				message = "Error occured in sending delete employee request";
				model.addAttribute("message", message);							
				return ("signup/saveData");
			}else if(e instanceof BankDeactivatedException)
			{
				throw new BankDeactivatedException(e.getMessage());
			} else {
			// TODO Auto-generated catch block
			e.printStackTrace();						
			message = "Error occured in sending delete request";
			model.addAttribute("message", message);				
			return ("signup/saveData");
			}
		 }		
	}
	
}
