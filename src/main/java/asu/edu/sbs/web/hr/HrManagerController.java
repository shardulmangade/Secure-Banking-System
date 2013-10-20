package asu.edu.sbs.web.hr;

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
import asu.edu.sbs.hr.service.HrDeptManager;

@Controller
@RequestMapping(value= "/signupemployee")
public class HrManagerController {
	
	@Autowired
	HrDeptManager hrmanager;
		
		@RequestMapping(value = "hr/hrmanager", method = RequestMethod.GET)
		public String addnewHrEmployee(Locale locale, Model model) {
			System.out.println("Inside hr manager Controller .............");				
			return "hr/manager/manager";
		} 
		
		
		@RequestMapping(value = "hr/hrmanager", method = RequestMethod.POST)
		public String addnewHrEmployeePost(Locale locale, Model model) {
			System.out.println("Inside hr manager post Controller .............");				
			return "hr/manager/hrmanager";
		}
		
		
		@RequestMapping(value = "/newhremployee", method = RequestMethod.POST)
		public ModelAndView newHrEmployeeGet(Locale locale, Model model) {
			System.out.println("Inside hr manager get Controller .............");				
			//return "hr/newhremployee";
			
			return new ModelAndView("hr/newhremployee", "signupemployee", new SignUpEmployee());
		}
		
		@RequestMapping(value = "/newhremployee/op1", method = RequestMethod.POST)
		public ModelAndView newHrEmployeePost(@ModelAttribute @Valid SignUpEmployee employee, BindingResult result, final RedirectAttributes attributes) {
			System.out.println("INSIDE hr manager post Controller .............");
			
			return new ModelAndView("hr/newhremployee", "signupemployee", new SignUpEmployee());
			
		}

		
		@RequestMapping(value = "/deletehremployee/op1" ,method = RequestMethod.POST)
		public String deleteEmployeeGet(Model model,HttpServletRequest request)
		{
			return  ("hr/deletehremployee");
		}
			
		
		@RequestMapping(value = "/deletehremployee" ,method = RequestMethod.POST)
		public String deleteEmployeePost(Model model,HttpServletRequest request)
		{
			System.out.println("\n Inside delete empployee post controller");
			String message ;
									
			try{												
				message= "Employee "+ request.getParameter("userNametext")+ "has been deleted";						
				hrmanager.deleteEmployeeRequest(request.getParameter("userNametext"));
				model.addAttribute("message", message);							
				return ("signup/saveData");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();						
				message = "Error occured in sending delete request";
				model.addAttribute("message", message);				
				return ("signup/saveData");						
			 }		
		}

		
}
