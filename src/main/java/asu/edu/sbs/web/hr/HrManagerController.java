package asu.edu.sbs.web.hr;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
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
		
		
		@RequestMapping(value = "hr/newhremployee", method = RequestMethod.GET)
		public String newHrEmployeeGet(Locale locale, Model model) {
			System.out.println("Inside hr manager post Controller .............");				
			return "hr/newhremployee";
		}
		
		@RequestMapping(value = "hr/newhremployee", method = RequestMethod.POST)
		public String newHrEmployeePost(Locale locale, Model model) {
			System.out.println("Inside hr manager post Controller .............");				
			return "signup/saveData";
		}
}
