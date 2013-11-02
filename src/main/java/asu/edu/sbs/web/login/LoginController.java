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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import asu.edu.sbs.domain.IBankRoles;
import asu.edu.sbs.domain.PasswordChange;
import asu.edu.sbs.domain.PasswordChangeValidator;
import asu.edu.sbs.exception.BankAccessException;
import asu.edu.sbs.exception.BankStorageException;
import asu.edu.sbs.login.service.LoginManager;


/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;

	@Autowired
	private LoginManager loginManager;

	@Autowired
	private	PasswordChangeValidator pwdValidator;

	/**
	 * Attach the custom validator to the Spring context
	 */
	@InitBinder("command")
	protected void initBinder(WebDataBinder binder) {

		binder.setValidator(pwdValidator);
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homePage(ModelMap model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();


		for (GrantedAuthority ga : authorities) {
			if(ga.getAuthority().equals(IBankRoles.ROLE_EXTERNAL_USER))
				return "redirect:/customer/firstlogin";
			if(ga.getAuthority().equals(IBankRoles.ROLE_IT_EMPLOYEE))
				return "redirect:/it/employee";
			if(ga.getAuthority().equals(IBankRoles.ROLE_IT_MANAGER))
				return "redirect:/it/manager";
			if(ga.getAuthority().equals(IBankRoles.ROLE_HR_EMPLOYEE))
				return "redirect:/hr/hremployee/hrEmployee";			
			if(ga.getAuthority().equals(IBankRoles.ROLE_HR_MANAGER))
				return "redirect:/hr/hrmanager/manager/op1";
			if(ga.getAuthority().equals(IBankRoles.ROLE_SALES_EMPLOYEE))
				return "redirect:/sales/salesemployee/salesemployee";			
			if(ga.getAuthority().equals(IBankRoles.ROLE_SALES_MANAGER))
				return "redirect:/sales/salesmanager/manager";
			if(ga.getAuthority().equals(IBankRoles.ROLE_CORPORATE_MANAGER))
				return "redirect:/corporate";
			if(ga.getAuthority().equals(IBankRoles.ROLE_TRANSACTION_EMPLOYEE))
				return "redirect:/transactions/regularEmployee/home";
			if(ga.getAuthority().equals(IBankRoles.ROLE_TRANSACTION_MANAGER))
				return "redirect://transactions/transactionManager/home";
			if(ga.getAuthority().equals(IBankRoles.ROLE_EXTERNAL_MERCHANT))
				return "redirect:/merchant/merchant/mainpage";
		}
		return "home";

	}


	/**
	 * A valid authenticated user is redirected to the otp page.
	 * 
	 * @return 		Returned to the home page of Quadriga.
	 * @throws BankAccessException 
	 * @throws BankStorageException 
	 */
	@RequestMapping(value = "/auth/otpcheck", method = RequestMethod.GET)
	public String createOTP(ModelMap model, Principal principal) throws BankAccessException, BankStorageException {

		String name = principal.getName();
		logger.info("The authenticated user <<"+name+">> entered the otp check stage !");

		//Check for existing OTP
		if(!loginManager.checkForvalidOTP(name))
		{
			// Create new OTP and email it for the user
			loginManager.insertNewOTP(name);
			model.addAttribute("newOTP",true);
		}		

		model.addAttribute("username", name);
		return "otp";

	}

	@RequestMapping(value = "/auth/otp", method = RequestMethod.POST)
	public String validateOTP(@RequestParam(value="otp") String otp, ModelMap model, Principal principal) throws BankStorageException {


		System.out.println("The authenticated user <<"+principal.getName()+">> submitted an OTP: "+otp);	

		//Check if OTP exists for user and then only proceed to validating OTP
		if(!loginManager.checkForvalidOTP(principal.getName()))
		{			
			// Create new OTP and email it for the user
			loginManager.insertNewOTP(principal.getName());

			// Set error message: OTP has expired and we have sent a new one
			model.addAttribute("newOTP",true);

			//Redirect to OTP page
			return "otp"; 
		}

		//The time period of OTP is valid. Now check for otp correctness
		if(loginManager.validateOTP(principal.getName(), otp))
		{
			//Set the new role to the user
			String role = loginManager.getRole(principal.getName());	

			List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
			authorityList.add(new SimpleGrantedAuthority(role));
			Authentication newAuth = new UsernamePasswordAuthenticationToken(principal,SecurityContextHolder.getContext().getAuthentication().getCredentials(),authorityList);
			SecurityContextHolder.getContext().setAuthentication(newAuth);

			//Remove the OTP from database as it has been validated
			loginManager.deleteOTP(principal.getName());

			//Redirect to home page based on role.
			if(role.equals(IBankRoles.ROLE_EXTERNAL_USER))
				return "redirect:/customer/firstlogin";
			if(role.equals(IBankRoles.ROLE_IT_EMPLOYEE))
				return "redirect:/it/employee";
			if(role.equals(IBankRoles.ROLE_IT_MANAGER))
				return "redirect:/it/manager";
			if(role.equals(IBankRoles.ROLE_HR_EMPLOYEE))
				return "redirect:/hr/hremployee/hrEmployee";			
			if(role.equals(IBankRoles.ROLE_HR_MANAGER))
				return "redirect:/hr/hrmanager/manager/op1";
			if(role.equals(IBankRoles.ROLE_SALES_EMPLOYEE))
				return "redirect:/sales/salesemployee/salesemployee";			
			if(role.equals(IBankRoles.ROLE_SALES_MANAGER))
				return "redirect:/sales/salesmanager/manager";
			if(role.equals(IBankRoles.ROLE_CORPORATE_MANAGER))
				return "redirect:/corporate";
			if(role.equals(IBankRoles.ROLE_TRANSACTION_EMPLOYEE))
				return "redirect:/transactions/regularEmployee/home";
			if(role.equals(IBankRoles.ROLE_TRANSACTION_MANAGER))
				return "redirect://transactions/transactionManager/home";
			if(role.equals(IBankRoles.ROLE_EXTERNAL_MERCHANT))
				return "redirect:/merchant/merchant/mainpage";
			//TODO: add default case

		}
		else
		{
			//OTP did not match
			logger.info("The authenticated user <<"+principal.getName()+">> OTP did not match");

			// Set error message and redirect to same page
			model.addAttribute("errorOTP",true);			
			return "otp";
		}

		return "otp";

	}

	

	@RequestMapping(value = "/pwd", method = RequestMethod.POST)
	public ModelAndView passwordChange(Principal principal) {
		ModelAndView model = new ModelAndView("pwdchange","command",new PasswordChange());
		return model;
	}

	@RequestMapping(value = "/pwdchange", method = RequestMethod.POST)
	public String passwordChangeRequest(@Validated @ModelAttribute("command")PasswordChange pwd, BindingResult result,  Principal principal, Model model) throws BankStorageException {

		if(result.hasErrors())
		{
			model.addAttribute("error", true);
		}
		else
		{
			if(loginManager.changePassword(principal.getName(), pwd.getNewPassword()) == SUCCESS)
			{
				model.addAttribute("success", true);
			}			
		}

		model.addAttribute("username", principal.getName());
		return "pwdnotification";
	}

}
