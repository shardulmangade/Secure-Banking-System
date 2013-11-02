package asu.edu.sbs.web.transaction;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import asu.edu.sbs.domain.Transaction;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.sales.service.SalesDeptManager;
import asu.edu.sbs.transaction.service.TransactionService;

@Controller
public class TransactionController {

	@Autowired
	private TransactionService transactionManager;
	
	@RequestMapping(value = "/transactions/regularEmployee/home", method = RequestMethod.GET)
	public String getRegEmployeePage(Locale locale, Model model, Principal principal) throws Exception
	{
		model.addAttribute("username",principal.getName());
		return "transactions/employee/regularEmployee";
	}
	
	@RequestMapping(value = "/transactions/regularEmployee/transactions", method = RequestMethod.POST)
	public String getTransactions(Locale locale, Model model, Principal principal) throws Exception
	{
//		transactionManager = new TransactionManager();
		String grantedTo = principal.getName();
		List<Transaction> transactions =  transactionManager.getRegEmpTransactions(grantedTo);
		model.addAttribute("transactions", transactions);
		model.addAttribute("username",principal.getName());
		return "transactions/employee/transactionsForRegEmp";
	}
	
	@RequestMapping(value="/transactions/regularEmployee/askPermission", method = RequestMethod.POST)
	public String getUsersForPermission(Locale locale, Model model, Principal principal) throws Exception
	{
		List<String> users = transactionManager.getUsersForPermission();
		model.addAttribute("users", users);
		model.addAttribute("username",principal.getName());
		return "transactions/employee/askPermission";
	}
	
	
	@RequestMapping(value = "/transactions/regularEmployee/makeUsersActive", method = RequestMethod.POST)
    public String pendingUserRequests(Locale locale, Model model, HttpServletRequest request, Principal principal) throws Exception
	{
		List<String> users = new ArrayList<String>();
		for(String user:request.getParameterValues("euser"))
			users.add(user);
		transactionManager.makeUsersActive(users, principal.getName());
	//	transactionManager.makeUsersActive(users, principal.getName());
		model.addAttribute("username",principal.getName());
		return "redirect:/transactions/regularEmployee/home";
	}
    

	
}
