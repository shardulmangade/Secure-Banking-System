package asu.edu.sbs.web.sales;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import asu.edu.sbs.domain.User;
import asu.edu.sbs.sales.service.SalesDeptManager;

@Controller
public class SalesEmployeeController {
	
	@RequestMapping(value = "/sales/emp/home", method = RequestMethod.GET)
	public String getsalesEmployee(Locale locale, Model model) {
		System.out.println("Inside sales employee Controller .............");
				
		return "sales/employee/home";
	}
}


/*@Autowired
private SalesDeptManager salesdept;
*/
/*@RequestMapping(value = "sales/employee/userrequests", method = RequestMethod.GET)
public String getUserRequests(Locale locale, Model model) {
	System.out.println("Inside employee Controller for sales.............");*/
	
/*	List<User> userRequests = salesdept.getAllUserRequests();
	model.addAttribute("userRequests", userRequests);
	return "sales/employee/requests";
}
	*/