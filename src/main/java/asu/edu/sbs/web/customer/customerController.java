package asu.edu.sbs.web.customer;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import asu.edu.sbs.customer.service.CustomerManager;
import asu.edu.sbs.domain.Credit;

@Scope(value="session")
@Controller
//@RequestMapping(value= "/customer")
public class customerController {
	
	@Autowired
	private CustomerManager customerManager;
	private PrivateKey privateKey ;
	private PublicKey publicKey ;
	
	@RequestMapping(value = "/customer/firstlogin", method = RequestMethod.GET)
	public String firstLogin(Locale locale, Model model) {
		try{
			System.out.println("Inside first login Controller .............");
			//generate private and public keys here
			KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("DSA");
			//SecureRandom sRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGenerator.initialize(1024);
			KeyPair keyPair = keyGenerator.generateKeyPair();
			privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
		}catch(Exception ex){
			//change the exception handling mechanism
			ex.printStackTrace();
		}		
		return "redirect:/customer/mainpage";
	}
	
	/**
	 * dummy method delete this later
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/customer/print", method = RequestMethod.GET)
	public String printOTP(Locale locale, Model model) {
		System.out.println("PRIVATE KEY  " + this.privateKey);
		System.out.println("PUBLIC KEY  " + this.publicKey);
				
		return "redirect:/customer/mainpage";
	}
	
	@RequestMapping(value = "/customer/mainpage", method = RequestMethod.GET)
	public String customerMainPage(Locale locale, Model model,Principal principal) {
		System.out.println("Inside customer post Controller .............");
		model.addAttribute("message", principal.getName());
		return "customer/mainpage";
	}
	
	
	@RequestMapping(value = "/customer/transaction", method = RequestMethod.POST)
	public String customerTransaction(Locale locale, Model model, Principal principle) {
		System.out.println("Inside customer transaction Controller .............");
		List<Credit> listTransactions=  customerManager.getAllTransaction(principle.getName());
		
		model.addAttribute("listTransactions", listTransactions);
		return "customer/viewtransaction";
	}
	
	
	@RequestMapping(value = "/customer/newtransaction", method = RequestMethod.POST)
	public String newCustomerTransaction(Locale locale, Model model, Principal principle) {
		System.out.println("Inside new transaction Controller .............");						
		return "customer/maketransaction";
	}
	
	@RequestMapping(value = "/customer/performTransaction", method = RequestMethod.POST)
	public String performTransaction( Locale locale, Model model, Principal principle,HttpServletRequest request,Principal principal) {
		System.out.println("Inside new transaction Controller .............");
		String message = null;
				
		try {
			if(request.getParameter("userNametext") !=null && request.getParameter("accountNumbertext") !=null && request.getParameter("amounttext")!=null ){
				String userName=request.getParameter("userNametext");
				String accountNumber = request.getParameter("accountNumbertext");
				Double amount= Double.parseDouble(request.getParameter("amounttext"));
				Credit credit = new Credit();
				if (customerManager.validateRecepientUser(userName, accountNumber)){
					credit.setFromCustomer(principal.getName());
					credit.setFromaccount(customerManager.getAccountNumberForCustomer(principal.getName()));	
					credit.setToacccount(accountNumber);
					credit.setAmount(amount);
					credit.setToCustomer(userName);
					//customerManager.insertNewTransaction(credit);
					//sign with private key
					String buffer = customerManager.getSignedRequest(this.privateKey, credit);			
					//buffer = buffer.substring(0, buffer.length() - 1) + "1";
					//credit.setSignedRequest(buffer);
					boolean verify = customerManager.verifyRequest(credit, buffer, publicKey);
					//set the public key concerned to the user for the transaction
					//credit.setPublicKey(this.publicKey.);
					//store to db
					if(verify){
						customerManager.insertNewTransaction(credit);
					}else{
						message = "The User is not Authentic!";
					}
					message = "Your Transaction is successfully processed.";
				}else {
					message = "There was error in processing your transaction.Please check username and accountNumber again";
				}
			}		
		} catch (Exception e){
			e.printStackTrace();
			message = "Sorry .we are unable to process your transaction right now";			
		}
		model.addAttribute("message", message);
		return "customer/performTransaction";
	}
	
	
}
