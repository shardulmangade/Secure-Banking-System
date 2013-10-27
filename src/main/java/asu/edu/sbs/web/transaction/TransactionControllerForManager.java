package asu.edu.sbs.web.transaction;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.activity.InvalidActivityException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import asu.edu.sbs.domain.SignUpEmployee;
import asu.edu.sbs.domain.Transaction;
import asu.edu.sbs.hr.service.HrDeptManager;
import asu.edu.sbs.transaction.service.TransactionServiceForManager;

@Controller
@RequestMapping(value= "/transactions/transactionManager")
public class TransactionControllerForManager {
	
	@Autowired
	TransactionServiceForManager transManager;
	
	ModelAndView savedMav;
		
		@RequestMapping(value = "home", method = RequestMethod.GET)
		public String addnewTransEmployee(Locale locale, Model model) {
			System.out.println("Inside trans manager Controller .............");				
			return "transactions/manager/transManager";
		} 
		
		@RequestMapping(value = "transactionsForManager", method = RequestMethod.POST)
		public String getTransactions(Locale locale, Model model) throws Exception
		{
//			transactionManager = new TransactionManager();
			List<Transaction> transactions =  transManager.getManagerTransactions();
			model.addAttribute("transactionsM", transactions);
			return "transactions/manager/transactionsForManager";
		}
		
		
		@RequestMapping(value = "transactions/transmanager1", method = RequestMethod.POST)
		public String addnewTransEmployeePost(Locale locale, Model model) {
			System.out.println("Inside trans manager post Controller .............");				
			return "transactions/manager/transmanager";
		}
		
		
		@RequestMapping(value = "newTransEmployee", method = RequestMethod.POST)
		public ModelAndView newTransEmployeeGet(Locale locale, Model model) {
			System.out.println("Inside trans manager get Controller .............");							
			savedMav = new ModelAndView("transactions/manager/newTransEmployee", "signupemployee", new SignUpEmployee());
			return savedMav;
		}
		
		@RequestMapping(value = "/newtransemployee/op1", method = RequestMethod.POST)
		public ModelAndView newHrEmployeePost(@ModelAttribute @Valid SignUpEmployee employee, BindingResult result, final RedirectAttributes attributes) {
			System.out.println("INSIDE trans manager post Controller .............");
			
			String message ;
			ModelAndView mav = new ModelAndView();
			try{				
				System.out.println("\n Inside Employee signup post controller");
				if(result.hasErrors())
				{
					//return new ModelAndView("hr/newhremployee", "signupemployee",employee);
					//return savedMav;
					return new ModelAndView("transactions/manager/manager","signupemployee",employee);
				}		 
						
				mav.setViewName("signup/saveData");
				message= "Your request has been submitted for approval";
				employee.setDepartment("TRANSACTIONS");
				employee.setPassword("temppassword");
				transManager.addNewHrEmployee(employee);
				mav.addObject("message", message);				
				return mav;
			}
		 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e instanceof com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException )
			{
				message = "Username already Exists.Choose a different username";
				mav.addObject("message", message);
				mav.setViewName("signup/saveData");		
				return mav;
			} else
			{
				message = "Error in saving your data.Please try again";
				mav.addObject("message", message);
				mav.setViewName("signup/saveData");		
				return mav;					
			}
		  } 
		}

		
		@RequestMapping(value = "/deletetransemployee/op1" ,method = RequestMethod.POST)
		public String deleteEmployeeGet(Model model,HttpServletRequest request)
		{
			return  ("transactions/deletetransemployee");
		}
			
		
		@RequestMapping(value = "deleteTransEmployee" ,method = RequestMethod.POST)
		public String deleteEmployeePost(Model model,HttpServletRequest request)
		{
			System.out.println("\n Inside delete empployee post controller");
			String message = null ,userName;
			userName = request.getParameter("userNametext");
			int status;
			try{				
				status = transManager.getDeleteApprovalStatus(userName, "TRANSACTIONS");
				if(status==1 )
				{					
					message = "Employee "+ userName+ " has been deleted after approval of corporate level manager";
					transManager.deleteHrEmployee(userName);					
				} else  if (status==0){
					message= "Employee "+ userName+ " delete request has not beeen approved by corporate level manager yet";											
				} else if (status==-1)
				{
					message= "Employee "+ userName+ " delete request has beeen sent for approval to corporate level manager";						
					transManager.insertDeleteRequesttoCM(userName,"HR",false);
				}
				model.addAttribute("message", message);							
				return ("signup/saveData");
				
			} catch (Exception e) {
				if(e instanceof InvalidActivityException )
				{
					e.printStackTrace();		
					message = "Error occured in deleting employee .Please use valid username";
					model.addAttribute("message", message);							
					return ("signup/saveData");
				} else {
				// TODO Auto-generated catch block
				e.printStackTrace();						
				message = "Error occured in sending delete request";
				model.addAttribute("message", message);				
				return ("signup/saveData");
				}
			 }		
		}
		
		
		@RequestMapping(value = "transferTransEmployee" ,method = RequestMethod.POST)
		public ModelAndView transferEmployeeGet(Model model,HttpServletRequest request)
		{								
			Map <String,String> department = new LinkedHashMap<String,String>();			
			department.put("sales", "Sales department");
			department.put("TM", "Transaction Management department");
			department.put("IT", "IT & Tech Support department");
			department.put("CM", "Company Managment department");
			model.addAttribute("departmentList", department);			
			return new ModelAndView("transactions/manager/transferTransEmployee", "signupemployee", new SignUpEmployee());
		}
		
		@RequestMapping(value = "/transfertransemployee/op1" ,method = RequestMethod.POST)
		public String transferTransEmployee( SignUpEmployee employee,Model model,HttpServletRequest request)
		{
			System.out.println("\n Inside delete empployee post controller");
			String message,department = null ;
									
			try{												
				message= "Employee "+ request.getParameter("userNametext")+ " has been transfered";					
				transManager.updateDepartmentOfEmployee(request.getParameter("userNametext"), employee.getDepartment());
				model.addAttribute("message", message);							
				return ("signup/saveData");
				
			} catch (Exception e) {
				
				if(e instanceof InvalidActivityException )
				{
					e.printStackTrace();		
					message = "Error occured in transferring employee .Please use valid username";
					model.addAttribute("message", message);							
					return ("signup/saveData");
				} else {
				// TODO Auto-generated catch block
				e.printStackTrace();						
				message = "Error occured in sending transfer request";
				model.addAttribute("message", message);				
				return ("signup/saveData");
				}
			 }		
		}
		
		
}
