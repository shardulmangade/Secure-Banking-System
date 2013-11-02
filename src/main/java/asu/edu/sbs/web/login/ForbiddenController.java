package asu.edu.sbs.web.login;

import java.security.Principal;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import asu.edu.sbs.domain.IBankRoles;

@Controller
public class ForbiddenController {

	@RequestMapping(value="/forbidden", method = RequestMethod.GET)
	public String forbiddenHandle(ModelMap model, Principal principal) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

		for (GrantedAuthority ga : authorities) {
			if (ga.getAuthority().equals(IBankRoles.ROLE_VALID_USER))
				return "redirect:/auth/otpcheck";
			else if(ga.getAuthority().equals(IBankRoles.ROLE_INVALID_USER))
			{
				//Automatically logout the user
				SecurityContextHolder.clearContext();
				return "exceptions/accountdeactivated";
			}
		}
		return "exceptions/accessissue";
	}
	
	@RequestMapping(value="/errors/404.html", method = RequestMethod.GET)
	public String errorPageHandle(ModelMap model, Principal principal) {

		return "exceptions/error404";
	}
}
