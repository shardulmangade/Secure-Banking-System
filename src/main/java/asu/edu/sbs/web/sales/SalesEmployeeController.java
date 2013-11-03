package asu.edu.sbs.web.sales;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import asu.edu.sbs.domain.User;
import asu.edu.sbs.exception.BankDeactivatedException;
import asu.edu.sbs.sales.service.SalesDeptManager;

@Controller
//@RequestMapping(value = "sales/salesemployee")
public class SalesEmployeeController {
	
	@RequestMapping(value = "/sales/salesemployee", method = RequestMethod.GET)
	public String getsalesEmployee(Locale locale, Model model,Principal principal)throws BankDeactivatedException {
		System.out.println("Inside sales employee Controller .............");
		model.addAttribute("username", principal.getName());
		return "sales/employee/home";
	}
}

