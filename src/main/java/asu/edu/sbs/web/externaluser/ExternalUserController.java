package asu.edu.sbs.web.externaluser;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ExternalUserController {

	private static final Logger logger = LoggerFactory.getLogger(ExternalUserController.class);
	
	@RequestMapping(value = "/euser/home", method = RequestMethod.GET)
	public String externalUserHome(ModelMap model, Principal principal) {
		logger.info("The external user "+principal.getName()+" accessed the users home page");
		model.addAttribute("username", principal.getName());
		return "external/user/home";
	}
}
