package asu.edu.sbs.web.corporatemanager;

import java.security.Principal;
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
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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
import asu.edu.sbs.email.EmailNotificationManager;
import asu.edu.sbs.exception.BankDeactivatedException;
import asu.edu.sbs.hr.service.CorporateManager;
import asu.edu.sbs.login.service.OneTimePassword;
import asu.edu.sbs.web.login.LoginController;

@Controller

public class CorporateController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
		
	@Autowired
	CorporateManager crManager;


	@Autowired
	private EmailNotificationManager enManager;
		
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/corporate", method = RequestMethod.GET)
	public String home(Locale locale, Model model, Principal principal) throws BankDeactivatedException {
		logger.info("Welcome to corporate page, locale is {}.", locale);
		model.addAttribute("username", principal.getName());
		return "corporate/home";
	}
	
	
	@RequestMapping(value="/corporate/op2",  method = RequestMethod.POST)
	public String getTest2(Locale locale, Model model) {
		logger.info("Welcome to corporate page, locale is {}.", locale);
		
		return "corporate/delete";
	}
	
	@RequestMapping(value="/corporate/op3",  method = RequestMethod.POST)
	public ModelAndView getTest3(Locale locale, Model model)throws BankDeactivatedException {
		
		Map <String,String> department = new LinkedHashMap<String,String>();			
		department.put("sales", "Sales department");
		department.put("TM", "Transaction Management department");
		department.put("IT", "IT & Tech Support department");
		department.put("CM", "Company Managment department");
		department.put("HR", "Human Resource department");
		model.addAttribute("departmentList", department);		
		Map <String,String> roleList = new LinkedHashMap<String,String>();			
		roleList.put("manager", "manager");
		roleList.put("employee", "employee");
		model.addAttribute("roleList", roleList);	
		return new ModelAndView("corporate/transfer", "signupemployee", new User());
		
	}
	
	@RequestMapping(value = "/corporate/op4" ,method = RequestMethod.POST)
	public String getPending(Locale locale, Model model)throws BankDeactivatedException{
		
		System.out.println("Inside corporate controler for pending request");
		
		List<SignUpEmployee> singupList = crManager.getAllPendingUserRequests();
//		/System.out.println(userRequests.get(0).getFirstName());
		model.addAttribute("userRequests", singupList);		
		return "corporate/pending";
	}
	
	@RequestMapping(value = "/corporate/op1" ,method = RequestMethod.POST)
	public ModelAndView getDataEmployee(Locale locale , Model model)throws BankDeactivatedException
	{
		System.out.println("\n Inside Employee signup controller");		
		
		Map <String,String> department = new LinkedHashMap<String,String>();
		department.put("HR", "HR department");
		department.put("sales", "Sales department");
		department.put("TM", "Transaction Management department");
		department.put("IT", "IT & Tech Support department");
		department.put("CM", "Company Managment department");
		model.addAttribute("departmentList", department);
		Map <String,String> roleList = new LinkedHashMap<String,String>();			
		roleList.put("manager", "manager");
		roleList.put("employee", "employee");
		model.addAttribute("roleList", roleList);	
		return new ModelAndView("corporate/add", "signupuser", new User());
	}	
	
	@RequestMapping(value = "/corporate/corporateadduser" ,method = RequestMethod.POST)
	public ModelAndView postDataEmployee(@ModelAttribute @Valid User employee, BindingResult result, final RedirectAttributes attributes, Principal principal)throws BankDeactivatedException
	 {
		String message ;
		ModelAndView mav = new ModelAndView();
		OneTimePassword otp = new OneTimePassword() ;
		try{				
			System.out.println("\n Inside Employee signup post controller");
			if(result.hasErrors())
			{
				message= "Please fill form properly, validation erros observed";
				mav.addObject("message", message);	
				mav.addObject("username", principal.getName());
				mav.setViewName("corporate/saveData");
				return mav;
			}		 
					
			mav.setViewName("corporate/saveData"); 
			message= "The user has been added to the system.";
			
			employee.setCreatedBy(principal.getName());				
			Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			String password = otp.getPassword();
			String hashedPassword = passwordEncoder.encodePassword(otp.getPassword(), null);
			
			crManager.insertValidUser(employee,hashedPassword,principal.getName());
			enManager.sendPassword(employee, password);
			mav.addObject("message", message);								
			mav.addObject("username", principal.getName());
			
			
			mav.addObject("message", message);				
			return mav;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e instanceof com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException )
			{
				message = "Username already Exists.Choose a different username";
				mav.addObject("message", message);
				mav.setViewName("corporate/saveData");		
				return mav;
			}else if (e instanceof BankDeactivatedException)
			{
				throw new BankDeactivatedException(e.getMessage());
			}
			else
			{
				message = "Error in saving your data.Please try again";
				mav.addObject("message", message);
				mav.setViewName("corporate/saveData");		
				return mav;
					
			}
		 }	
	 }	

	
	@RequestMapping(value = "/corporate/corporateUpdate" ,method = RequestMethod.POST)
	public String transferUserCorporate(User employee,Model model,HttpServletRequest request,Principal principal)throws BankDeactivatedException
	{
		System.out.println("\n Inside corporate transfer empployee post controller");
		String message,department=null,username = null ;
		String tobeReplaced = "";				
		username=request.getParameter("userNametext");
		try{												
			message= "Employee "+ request.getParameter("userNametext")+ " has been transfered";
			if(employee.getDepartment().equals("NONE") || employee.getRole().equals("NONE"))
			{
				message="Oops!! You seem to be lost because of some Bad Operation. Please press the Home button to return to your mainpage or Logout.";
				model.addAttribute("message", message);
				model.addAttribute("username", principal.getName());
				return ("corporate/saveData");	
			}
			tobeReplaced = crManager.getRoleTobechanged(employee.getDepartment(),employee.getRole());
			crManager.updateUserRole(tobeReplaced,employee.getDepartment(),username ,principal.getName());
			model.addAttribute("message", message);							
			return ("corporate/saveData");
			
		} catch (Exception e) {
			
			if(e instanceof InvalidActivityException )
			{
				e.printStackTrace();		
				message = "Error occured in transferring employee .Please use valid username";
				model.addAttribute("message", message);							
				return ("corporate/saveData");
			}
			else if (e instanceof BankDeactivatedException)
			{
				throw new BankDeactivatedException(e.getMessage());
			}
			else {
			// TODO Auto-generated catch block
			e.printStackTrace();						
			message = "Error occured in sending transfer request";
			model.addAttribute("message", message);				
			return ("corporate/saveData");
			}
		 }		
	}

	@RequestMapping(value = "/corporate/pending", method = RequestMethod.POST)
	public String pendingResponse(Locale locale, Model model, Principal principal, HttpServletRequest request)throws BankDeactivatedException {
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
					crManager.deleteEmployee(username, principal.getName());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Delete from both tables
			}
			return "/corporate/home";
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
			return "/corporate/home";
			
		}	
		return "corporate/home";
	}

}

