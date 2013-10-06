package asu.edu.sbs.web.trial;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import asu.edu.sbs.domain.User;
import asu.edu.sbs.service.TrialUserManager;

@Controller
public class ControllerRam {

	@Autowired
	private TrialUserManager usermanager;
	
	@RequestMapping(value = "/ram", method = RequestMethod.GET)
	public String trial(Locale locale, Model model) {
		System.out.println("Inside Controller created by ram.............");
		
		return "trial/ram";
	}
	
	
	@RequestMapping(value = "/ramdb", method = RequestMethod.GET)
	public String trialDB(Locale locale, Model model) {
		System.out.println("This controller will access the database...........");
		User user = usermanager.getUser("123");
		
		//Model is Hashmap. The key can be of any text "User" or "abc" or "xxx".
		model.addAttribute("User", user);
		return "trial/ram";
	}
}
