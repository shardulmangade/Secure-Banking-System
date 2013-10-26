package asu.edu.sbs.web.hr;

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
	public String getHrEmployee(Locale locale, Model model) {
		System.out.println("Inside hr employee Controller .............");
				
		return "hr/employee/hrEmployee";
	}
}
