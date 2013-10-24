package asu.edu.sbs.web.customer;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

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
	
	
	@RequestMapping(value = "customer/newtransaction", method = RequestMethod.POST)
	public String newCustomerTransaction(Locale locale, Model model, Principal principle) {
		System.out.println("Inside new transaction Controller .............");						
		return "customer/maketransaction";
	}
	
	@RequestMapping(value = "customer/performTransaction", method = RequestMethod.POST)
	public String performTransaction(Locale locale, Model model, Principal principle,HttpServletRequest request) {
		System.out.println("Inside new transaction Controller .............");
		String userName=request.getParameter("userNametext");
		String accountNumber = request.getParameter("accountNumbertext");
		String amount= request.getParameter("amounttext");
		
		if(userName!=null && accountNumber!= null && amount != null)
		{
			
		}
		
		return "customer/performTransaction";
	}
	
	
}
