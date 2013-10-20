package asu.edu.sbs.web.hr;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import asu.edu.sbs.domain.SignUpEmployee;

@Controller
@RequestMapping(value= "/signupemployee")
public class HrManagerController {
		
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
		
		
		@RequestMapping(value = "/newhremployee", method = RequestMethod.GET)
		public String newHrEmployeeGet(Locale locale, Model model) {
			System.out.println("Inside hr manager get Controller .............");				
			return "hr/newhremployee";
		}
		
//		@RequestMapping(value = "/newhremployee", method = RequestMethod.POST)
//		public String newHrEmployeePost(@ModelAttribute @Valid SignUpEmployee employee, BindingResult result, final RedirectAttributes attributes) {
//			System.out.println("INSIDE hr manager post Controller .............");
//		
//			return "signup/saveData";
//		}
}
