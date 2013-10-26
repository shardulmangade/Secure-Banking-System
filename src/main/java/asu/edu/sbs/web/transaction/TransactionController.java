package asu.edu.sbs.web.transaction;

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
	
	@RequestMapping(value = "regularEmployee", method = RequestMethod.GET)
	public String getRegEmployeePage(Locale locale, Model model) throws Exception
	{
		return "transactions/employee/regularEmployee";
	}
	
	@RequestMapping(value = "transactionsForRegEmp", method = RequestMethod.POST)
	public String getTransactions(Locale locale, Model model) throws Exception
	{
//		transactionManager = new TransactionManager();
		List<Transaction> transactions =  transactionManager.getRegEmpTransactions();
		model.addAttribute("transactions", transactions);
		return "transactions/employee/transactionsForRegEmp";
	}
	
	@RequestMapping(value="askPermissionRegEmp", method = RequestMethod.POST)
	public String getUsersForPermission(Locale locale, Model model) throws Exception
	{
		List<String> users = transactionManager.getUsersForPermission();
		model.addAttribute("users", users);
		return "transactions/employee/askPermission";
	}
	
	
	@RequestMapping(value = "makeUsersActive", method = RequestMethod.POST)
    public String pendingUserRequests(Locale locale, Model model, HttpServletRequest request) throws Exception
	{
		List<String> users = new ArrayList<String>();
		for(String user:request.getParameterValues("euser"))
			users.add(user);
		
		transactionManager.makeUsersActive(users);
		
		return "redirect:regularEmployee";
	}
    

	
}
