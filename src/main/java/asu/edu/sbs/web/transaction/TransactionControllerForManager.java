package asu.edu.sbs.web.transaction;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.activity.InvalidActivityException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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
import asu.edu.sbs.domain.User;
import asu.edu.sbs.email.EmailNotificationManager;
import asu.edu.sbs.exception.BankAccessException;
import asu.edu.sbs.hr.service.HrDeptManager;
import asu.edu.sbs.login.service.OneTimePassword;
import asu.edu.sbs.transaction.service.TransactionServiceForManager;

@Controller
@RequestMapping(value= "/transactions/transactionManager")
public class TransactionControllerForManager {
	
	@Autowired
	TransactionServiceForManager transManager;
	
	
	@Autowired
	private EmailNotificationManager enManager;
	
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
			savedMav = new ModelAndView("transactions/manager/newTransEmployee", "signupemployee", new User());
			return savedMav;
		}
		
		@RequestMapping(value = "/newtransemployee/op1", method = RequestMethod.POST)
		public ModelAndView newTransEmployeePost(@ModelAttribute @Valid User user, BindingResult result, final RedirectAttributes attributes,Principal principal) {
			System.out.println("INSIDE trans manager post Controller for new employee .............");
			OneTimePassword otp = new OneTimePassword() ;
			String message ;
			ModelAndView mav = new ModelAndView();
			try{				
				System.out.println("\n Inside Employee signup post controller");
				if(result.hasErrors())
				{
					//return new ModelAndView("hr/newhremployee", "signupemployee",employee);
					//return savedMav;
					//return new ModelAndView("hr/manager/manager","signupemployee",user);
					message = "Validation Errors observed.Please go back and fill valid information";
					mav.addObject("message", message);
					mav.setViewName("signup/saveData");
					return mav;
					//return new ModelAndView("signup/saveData", "signupemployee",user);
				}		 
						
				mav.setViewName("signup/saveData");
				message= "Your request has been submitted for approval";
				user.setDepartment("TM");
				user.setRole("ROLE_TRANSACTION_EMPLOYEE");				
				user.setCreatedBy(principal.getName());				
				Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
				String password = otp.getPassword();
				String hashedPassword = passwordEncoder.encodePassword(otp.getPassword(), null);
				
				transManager.insertValidUser(user,hashedPassword,principal.getName());
				enManager.sendPassword(user, password);
				mav.addObject("message", message);								
				mav.addObject("username", principal.getName());
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
				mav.addObject("username",principal.getName() );			
				return mav;
			} else
			{
				message = "Error in saving your data.Please try again";
				mav.addObject("message", message);
				mav.setViewName("signup/saveData");
				mav.addObject("username",principal.getName() );
				return mav;					
			}
		  }
			/*System.out.println("INSIDE trans manager post Controller .............");
			OneTimePassword otp = new OneTimePassword() ;
			String message ;
			ModelAndView mav = new ModelAndView();
			try{				
				System.out.println("\n Inside Employee signup post controller");
				if(result.hasErrors())
				{
					//return new ModelAndView("hr/newhremployee", "signupemployee",employee);
					//return savedMav;
					
					message = "Validation Errors observed.Please go back and fill valid information";
					mav.addObject("message", message);
					mav.setViewName("signup/saveData");
					return mav;
					//return new ModelAndView("transactions/manager/manager","signupemployee",employee);
				}		 
						
				mav.setViewName("signup/saveData");
				message= "Your request has been submitted for approval";
				employee.setDepartment("TM");
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
		  }*/ 
		}

		
		@RequestMapping(value = "/deletetransemployee/op1" ,method = RequestMethod.POST)
		public String deleteEmployeeGet(Model model,HttpServletRequest request)
		{
			return  ("transactions/manager/deleteTransEmployee");
		}
			
		
		@RequestMapping(value = "deleteTransEmployee" ,method = RequestMethod.POST)
		public String deleteEmployeePost(Model model,HttpServletRequest request)
		{
			System.out.println("\n Inside delete empployee post controller");
			String message = null ,userName;
			userName = request.getParameter("userNametext");
			int status;
			try{
				if(userName.equals(null))
					throw new BankAccessException("Please enter the username...");
				status = transManager.getDeleteApprovalStatus(userName, "TRANSACTIONS");
				if(status==1 )
				{					
					message = "Employee "+ userName+ " has been deleted after approval of corporate level manager";
					transManager.deleteTransEmployee(userName);					
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
		public ModelAndView transferEmployeeGet(Model model,HttpServletRequest request, Principal principal)
		{								
			Map <String,String> department = new LinkedHashMap<String,String>();			
			department.put("sales", "Sales department");
			department.put("TM", "Transaction Management department");
			department.put("IT", "IT & Tech Support department");
			department.put("CM", "Company Managment department");
			model.addAttribute("departmentList", department);	
			
			model.addAttribute("username", principal.getName());
			
			Map <String,String> roleList = new LinkedHashMap<String,String>();			
			roleList.put("manager", "manager");
			roleList.put("employee", "employee");
			model.addAttribute("roleList", roleList);	
			
			return new ModelAndView("transactions/manager/transferTransEmployee", "signupemployee", new User());
		}
		
		@RequestMapping(value = "/transfertransemployee/op1" ,method = RequestMethod.POST)
		public String transferTransEmployee( User user,Model model,HttpServletRequest request, Principal principal)
		{
			System.out.println("\n Inside delete empployee post controller");
			String message,department = null, username=null ;
			String roleToBeupdated =null;
			username=request.getParameter("userNametext");
									
			try{												
				message= "Employee "+ username + " has been transfered";
				if(user.getDepartment().equals("NONE") || user.getRole().equals("NONE"))
				{
					message="Oops!! You seem to be lost because of some Bad Operation. Please press the Home button to return to your mainpage or Logout.";
					model.addAttribute("message", message);
					model.addAttribute("username", principal.getName());
					return ("it/manager/saveData");	
				}
				roleToBeupdated = transManager.getRoleTobechanged(user.getDepartment(),user.getRole());
				//transManager.updateDepartmentOfEmployee(request.getParameter("userNametext"), employee.getDepartment());
				transManager.updateUserRole(roleToBeupdated,"TM",user.getDepartment(),username ,principal.getName());
				model.addAttribute("message", message);	
				model.addAttribute("username", principal.getName());
				return ("signup/saveData");
				
			} catch (Exception e) {
				
				if(e instanceof InvalidActivityException )
				{
					e.printStackTrace();		
					message = "Error occured in transferring employee .Please use valid username";
					model.addAttribute("message", message);	
					model.addAttribute("username", principal.getName());
					return ("signup/saveData");
				} else {
				// TODO Auto-generated catch block
				e.printStackTrace();						
				message = "Error occured in sending transfer request";
				model.addAttribute("message", message);	
				model.addAttribute("username", principal.getName());
				return ("signup/saveData");
				}
			 }		
		}
		
		
}
