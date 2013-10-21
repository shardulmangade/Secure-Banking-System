package asu.edu.sbs.web.corporatemanager;


import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import asu.edu.sbs.domain.SignUpEmployee;
import asu.edu.sbs.domain.SignUpUser;
import asu.edu.sbs.domain.Subscriber;
import asu.edu.sbs.hr.service.CorporateManager;
import asu.edu.sbs.hr.service.HrDeptManager;
import asu.edu.sbs.web.trial.HomeController;

@Controller
public class CorporateController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
		
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
	public String getTest3(Locale locale, Model model) {
		logger.info("Welcome to corporate page, locale is {}.", locale);		
		return "corporate/transfer";
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
	
	/**
	 * This method adds the user to database on behalf of 
	 * @param employee
	 * @param result
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/corporateadduser" ,method = RequestMethod.POST)
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
	
	
	
	
	
}
