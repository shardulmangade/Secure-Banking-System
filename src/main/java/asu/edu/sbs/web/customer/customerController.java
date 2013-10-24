package asu.edu.sbs.web.customer;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import asu.edu.sbs.customer.service.CustomerManager;
import asu.edu.sbs.domain.Credit;
import asu.edu.sbs.sales.service.SalesDeptManager;

@Controller
@RequestMapping(value= "/customer")
public class customerController {
	
	@Autowired
	private CustomerManager customerManager;

	@RequestMapping(value = "customer/mainpage", method = RequestMethod.GET)
	public String customerMainPage(Locale locale, Model model) {
		System.out.println("Inside customer post Controller .............");				
		return "customer/mainpage";
	}
	
	
	@RequestMapping(value = "customer/transaction", method = RequestMethod.POST)
	public String customerTransaction(Locale locale, Model model, Principal principle) {
		System.out.println("Inside customer transaction Controller .............");
		List<Credit> listTransactions=  customerManager.getAllTransaction("girish");
		
		model.addAttribute("listTransactions", listTransactions);
		return "customer/viewtransaction";
	}
	
}
