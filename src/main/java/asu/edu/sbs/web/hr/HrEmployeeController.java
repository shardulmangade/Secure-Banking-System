package asu.edu.sbs.web.hr;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import asu.edu.sbs.domain.User;


@Controller
@RequestMapping(value = "hr/hremployee")
public class HrEmployeeController {
	
	@RequestMapping(value = "/hrEmployee", method = RequestMethod.GET)
	public String getHrEmployee(Locale locale, Model model, Principal principal) {
		System.out.println("Inside hr employee Controller .............");
		model.addAttribute("username", principal.getName());		
		return "hr/employee/hrEmployee";
	}
}
