package asu.edu.sbs.web.login;

import java.security.Principal;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		System.out.println("Hellooooooooooooooooooooo");
		
		return "home";
	}
	
	/**
	 * A valid authenticated user is redirected to the home page.
	 * 
	 * @return 		Returned to the home page of Quadriga.
	 */
	@RequestMapping(value = "/auth/welcome", method = RequestMethod.GET)
	public String validUserHandle(ModelMap model, Principal principal) {

		String name = principal.getName();
		model.addAttribute("username", name);
		model.addAttribute("message", "Spring Security Custom Form example");
		return "hello";

	}

	/**
	 * User requests a login page
	 * 
	 * @return		Redirected to the login page
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model) {

		return "home";

	}

	/**
	 * Authentication failed. User credentials mismatch causes this request.
	 * 
	 * @return		Redirected to login page
	 */
	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {

		model.addAttribute("error", "true");
		return "home";

	}

	/**
	 * A authenticated user is logged out of the system.
	 * 
	 * @return		Redirect to login page
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {

		return "home";

	}
	
}
