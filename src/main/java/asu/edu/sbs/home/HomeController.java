package asu.edu.sbs.home;

import java.util.Collection;
import java.util.Locale;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import asu.edu.sbs.domain.IBankRoles;

@Controller
public class HomeController {
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
		 * Simply selects the home view to render by returning its name.
		 */
		@RequestMapping(value = "/", method = RequestMethod.GET)
		public String home(Locale locale, Model model) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();


			for (GrantedAuthority ga : authorities) {
				if(ga.getAuthority().equals(IBankRoles.ROLE_VALID_USER))
					return "redirect:/auth/otpcheck";
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
		 * A authenticated user is logged out of the system.
		 * 
		 * @return		Redirect to login page
		 */
		@RequestMapping(value = "/logout", method = RequestMethod.GET)
		public String logout(ModelMap model) {
	
			return "home";
	
		}
}

