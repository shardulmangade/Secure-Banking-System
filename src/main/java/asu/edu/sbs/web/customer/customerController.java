package asu.edu.sbs.web.customer;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String performTransaction( Locale locale, Model model, Principal principle,HttpServletRequest request) {
		System.out.println("Inside new transaction Controller .............");
		String message = null;
				
		try {
		
		if(request.getParameter("userNametext") !=null && request.getParameter("accountNumbertext") !=null && request.getParameter("amounttext")!=null ){
			String userName=request.getParameter("userNametext");
			String accountNumber = request.getParameter("accountNumbertext");
			Double amount= Double.parseDouble(request.getParameter("amounttext"));
			Credit credit = new Credit();
			if (customerManager.validateRecepientUser(userName, accountNumber))
			{
			credit.setFromCustomer("girish");
			credit.setFromaccount("9876543210");
			credit.setToacccount(accountNumber);
			credit.setAmount(amount);
			credit.setToCustomer(userName);
			customerManager.insertNewTransaction(credit);
			message = "Your Transaction is successfully processed.";
			}  else {
			message = "There was error in processing your transaction.Please check username and accountNumber again";
			}
		}		
		} catch (Exception e)
		{
			e.printStackTrace();
			message = "Sorry .we are unable to process your transaction right now";
			
		}
		model.addAttribute("message", message);
		return "customer/performTransaction";
	}
	
	
}
