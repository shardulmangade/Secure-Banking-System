package asu.edu.sbs.web.trial;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import asu.edu.sbs.customer.service.CustomerManager;
import asu.edu.sbs.domain.Credit;
import asu.edu.sbs.login.service.OneTimePassword;
import asu.edu.sbs.sales.service.SalesDeptManager;

@Scope(value="session")
@Controller
@RequestMapping(value= "/dummy")
public class DummyCustomerController {

@Autowired
private CustomerManager customerManager;
private PrivateKey privateKey ;
private PublicKey publicKey ;

@RequestMapping(value = "/customer/firstlogin", method = RequestMethod.GET)
public String firstLogin(Locale locale, Model model) {
	try{
		System.out.println("Inside first login Controller .............");
		//generate private and public keys here
		KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("DSA", "SUN");
		SecureRandom sRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
		keyGenerator.initialize(1024,  sRandom);
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

/**
 * This is the dummy method  delete this
 * @param locale
 * @param model
 * @param principle
 * @return
 */
@RequestMapping(value="/customer/approveTransaction", method = RequestMethod.POST)
public String submitTranscation(Locale locale, Model model, Principal principle,HttpServletRequest request,Principal principal){
	String message = null;
	try{
		if(request.getParameter("userNametext") !=null && request.getParameter("accountNumbertext") !=null && request.getParameter("amounttext")!=null ){
			//gather the information to be encrypted
			String userName=request.getParameter("userNametext");
			String accountNumber = request.getParameter("accountNumbertext");
			Double amount= Double.parseDouble(request.getParameter("amounttext"));
			Credit credit = new Credit();
			if (customerManager.validateRecepientUser(userName, accountNumber))
			{
				credit.setFromCustomer(principal.getName());	
				credit.setFromaccount("9876543210");	
				credit.setToacccount(accountNumber);
				credit.setAmount(amount);
				credit.setToCustomer(userName);
				
				//sign the request. Push this code to manager for this controller
				Signature dsa = Signature.getInstance("SHA1withDSA", "SUN"); 
				dsa.initSign(this.privateKey);
				String toEncrypt = credit.toString();
				InputStream is = new ByteArrayInputStream(toEncrypt.getBytes());
				BufferedInputStream bufin = new BufferedInputStream(is);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = bufin.read(buffer)) >= 0) {
				    dsa.update(buffer, 0, len);
				}
				bufin.close();
				
				credit.setSignedRequest(buffer);
				
				//push to db
				//change this code to push the 
				customerManager.insertNewTransaction(credit);
				message = "Your Transaction is successfully processed.";
			}  else {
				message = "There was error in processing your transaction.Please check username and accountNumber again";
			}
			
		}
	}catch(Exception ex){
		ex.printStackTrace();
	}
	model.addAttribute("message", message);
	return "customer/performTransaction";
}

	@RequestMapping(value ="customer/pki/auth" , method = RequestMethod.GET)
	public String authorizeUser(){
		
		return "redirect:customer/performTransaction";
	}

@RequestMapping(value = "/customer/mainpage", method = RequestMethod.GET)
	public String customerMainPage(Locale locale, Model model) {
	System.out.println("Inside customer post Controller .............");				
	return "customer/mainpage";
	}


@RequestMapping(value = "/customer/transaction", method = RequestMethod.POST)
public String customerTransaction(Locale locale, Model model, Principal principle) {
	System.out.println("Inside customer transaction Controller .............");
	List<Credit> listTransactions=  customerManager.getAllTransaction("girish");
	
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
			if (customerManager.validateRecepientUser(userName, accountNumber))
			{
				//credit.setFromCustomer("admin");
				//credit.setFromaccount("9876543210");
				credit.setFromCustomer(principal.getName());	
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