package asu.edu.sbs.web.corporatemanager;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import asu.edu.sbs.domain.SignUpEmployee;
import asu.edu.sbs.domain.Subscriber;

@Controller
public class AddUserController {


	@RequestMapping(value="/addusercorporate",  method = RequestMethod.POST)
	public String addUser(@Valid SignUpEmployee employee, BindingResult result, Model m) {
		
		if(result.hasErrors()) {
			return "corporate/add";
		}
		
		//m.addAttribute("message", "Successfully saved person: " + employee.toString());
		return "corporate/add";
	}
	
	@RequestMapping(value="/addusercorporate",  method = RequestMethod.GET)
	public String addUserGet(@Valid SignUpEmployee employee, BindingResult result, Model m) {
		m.addAttribute("subscriber", new Subscriber());
		return "corporate/add";
	}
}
