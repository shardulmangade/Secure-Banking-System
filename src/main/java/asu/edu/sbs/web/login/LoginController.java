package asu.edu.sbs.web.login;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import asu.edu.sbs.domain.IBankRoles;
import asu.edu.sbs.login.service.LoginManager;


/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private LoginManager loginManager;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {		
		return "home";
	}
	
	
	/**
	 * A valid authenticated user is redirected to the otp page.
	 * 
	 * @return 		Returned to the home page of Quadriga.
	 */
	@RequestMapping(value = "/auth/otpcheck", method = RequestMethod.GET)
	public String createOTP(ModelMap model, Principal principal) {

		String name = principal.getName();
		logger.info("The authenticated user "+name+" entered the otp check stage !");
		
		//TODO: Check for existing OTP
		//TODO: Create new OTP and email it for the user
		
		//TODO: Provide option to resend OTP
		
		loginManager.insertNewOTP(name);
		
		model.addAttribute("username", name);
		return "otp";

	}
	
	@RequestMapping(value = "/auth/otp", method = RequestMethod.POST)
	public String validateOTP(@RequestParam(value="otp") String otp, ModelMap model, Principal principal) {

		System.out.println("The authenticated user "+principal.getName()+" submitted an OTP: "+otp);	
		
		//TODO:Check if OTP exists for user and then only proceed to validating OTP
		if(loginManager.validateOTP(principal.getName(), otp))
		{
			//Set the new role to the user
			String role = loginManager.getRole(principal.getName());			
			List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
			authorityList.add(new SimpleGrantedAuthority(role));
			Authentication newAuth = new UsernamePasswordAuthenticationToken(principal,SecurityContextHolder.getContext().getAuthentication().getCredentials(),authorityList);
			SecurityContextHolder.getContext().setAuthentication(newAuth);
			
			Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
			System.out.println("------------Begin print of user roles------------");
			for (GrantedAuthority ga : authorities) {
				System.out.println(ga.getAuthority());
			}
			System.out.println("------------End print of user roles------------");
			
			//TODO: Remove the OTP from database as it has been validated
			
			//Redirect to home page based on role.
			if(role.equals(IBankRoles.ROLE_EXTERNAL_USER))
				return "redirect:/euser/home";
			//TODO: add default case
				
		}
		else
		{
			//OTP did not match
			System.out.println("The authenticated user "+principal.getName()+" OTP did not match");
			//TODO: Set error message and redirect to same page
		}
		return "otp";

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
