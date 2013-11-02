package asu.edu.sbs.web.merchant;

import java.security.Principal;
import java.util.ArrayList;
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
import asu.edu.sbs.domain.MerchantCredit;
import asu.edu.sbs.domain.MerchantTransaction;
import asu.edu.sbs.merchant.service.MerchantManager;
import asu.edu.sbs.sales.service.SalesDeptManager;

@Controller
@RequestMapping(value= "/merchant")
public class merchantController {
	
	@Autowired
	private MerchantManager merchantManager;

	@RequestMapping(value = "merchant/mainpage", method = RequestMethod.GET)
	public String customerMainPage(Locale locale, Model model, Principal principal) {
		System.out.println("Inside merchant post Controller .............");
		String name = principal.getName();
		model.addAttribute("username",name);
		return "merchant/mainpage";
	}
	
	
	@RequestMapping(value = "merchant/transaction", method = RequestMethod.POST)
	public String customerTransaction(Locale locale, Model model, Principal principal) {
		System.out.println("Inside merchant transaction Controller .............");
		String name = principal.getName();
		
		List<Credit> listTransactions=  merchantManager.getAllTransaction(name);
		
		model.addAttribute("listTransactions", listTransactions);
		model.addAttribute("username",name);
		return "merchant/viewtransaction";
	}

	@RequestMapping(value = "merchant/clearCustomerPendingTransactions", method = RequestMethod.POST)
	public String clearPendingTransaction(Locale locale, Model model, Principal principal) {
		System.out.println("Inside merchant transaction Controller .............");
		String name = principal.getName();
		List<MerchantCredit> listTransactions=  merchantManager.getPendingRequest(name);		
		model.addAttribute("listTransactions", listTransactions);
		model.addAttribute("username",name);
		return "merchant/clearCustomerPendingTransactions";
	}
	
	@RequestMapping(value = "merchant/newtransaction", method = RequestMethod.POST)
	public String newCustomerTransaction(Locale locale, Model model, Principal principal) {
		System.out.println("Inside new transaction Controller .............");
		String name = principal.getName();
		model.addAttribute("username",name);
		return "merchant/maketransaction";
	}
	
	@RequestMapping(value = "merchant/performTransaction", method = RequestMethod.POST)
	public String performTransaction( Locale locale, Model model, Principal principal,HttpServletRequest request) {
		System.out.println("Inside new transaction Controller .............");
		String name = principal.getName();
		String message = null;
				
		try {
		
		if(request.getParameter("userNametext") !=null && request.getParameter("accountNumbertext") !=null && request.getParameter("amounttext")!=null ){
			String userName=request.getParameter("userNametext");
			String accountNumber = request.getParameter("accountNumbertext");
			Double amount= Double.parseDouble(request.getParameter("amounttext"));
			Credit credit = new Credit();
			if (merchantManager.validateRecepientUser(userName, accountNumber))
			{
			credit.setFromCustomer("girish");
			credit.setFromaccount("9876543210");
			credit.setToacccount(accountNumber);
			credit.setAmount(amount);
			credit.setToCustomer(userName);
			merchantManager.insertNewTransaction(credit);
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
		model.addAttribute("username",name);
		return "merchant/performTransaction";
	}
	
	/**
	 * This method handles the pending request 
	 */
	@RequestMapping(value = "/merchant/handlePendingRequestsResponse", method = RequestMethod.POST)
	public String approvePendingReq( Locale locale, Model model, Principal principal,HttpServletRequest request) {	
		System.out.println("Inside aprove transaction Controller .............");
		String userName = principal.getName();
		String message = null;				
		try {
			//verify sig for each request
			ArrayList<MerchantCredit> requestList = merchantManager.getPendingRequest(userName);
			merchantManager.verifyAprrove(requestList);
			//add transaction to customer table
			//change account balance
		} catch (Exception e){
			e.printStackTrace();
			message = "Sorry .we are unable to process your transaction right now";			
		}
		model.addAttribute("message", message);
		//model.addAttribute("username",name);
		return "merchant/performTransaction";
		
	}
	
	
}
