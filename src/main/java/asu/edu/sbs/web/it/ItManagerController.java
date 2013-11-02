package asu.edu.sbs.web.it;

import java.security.Principal;
import java.util.LinkedHashMap;
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

import asu.edu.sbs.domain.IDepartments;
import asu.edu.sbs.domain.SignUpEmployee;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.email.EmailNotificationManager;
import asu.edu.sbs.exception.BankAccessException;
import asu.edu.sbs.it.service.ItDeptManager;
import asu.edu.sbs.login.service.OneTimePassword;

@Controller
public class ItManagerController {
	
	@Autowired
	ItDeptManager itmanager;
	ModelAndView savedMav;
	
	@Autowired
	private EmailNotificationManager enManager;
		
			
		@RequestMapping(value = "it/manager", method = RequestMethod.GET)
		public String ItManager(Locale locale, Model model, Principal principal) {
			System.out.println("Inside it manager Controller .............");
			String name = principal.getName();
			model.addAttribute("username", name);
			return "it/manager/manager";
		} 

		@RequestMapping(value = "it/manager/newitemployee", method = RequestMethod.POST)
		public ModelAndView newHrEmployeeGet(Locale locale, Model model,Principal principal) {
			ModelAndView savedMav;
			System.out.println("Inside IT manager get Controller .............");							
			savedMav = new ModelAndView("it/manager/newitemployee", "signupemployee", new User());
			model.addAttribute("username", principal.getName());
			return savedMav;
		}
		
		@RequestMapping(value = "it/manager/newitemployee/op1", method = RequestMethod.POST)
		public ModelAndView newHrEmployeePost(@ModelAttribute @Valid User user, BindingResult result, final RedirectAttributes attributes,Principal principal) {
			System.out.println("INSIDE IT manager post Controller .............");
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
					mav.setViewName("it/manager/saveData");
					return mav;
					//return new ModelAndView("signup/saveData", "signupemployee",user);
				}		 
						
				mav.setViewName("it/manager/saveData");
				message= "Your request has been submitted for approval";
				user.setDepartment("IT");
				user.setRole("ROLE_IT_EMPLOYEE");				
				user.setCreatedBy(principal.getName());				
				Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
				String password = otp.getPassword();
				String hashedPassword = passwordEncoder.encodePassword(otp.getPassword(), null);
				
				itmanager.insertValidUser(user,hashedPassword,principal.getName());
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
				mav.setViewName("it/manager/saveData");
				mav.addObject("username",principal.getName() );			
				return mav;
			} else
			{
				message = "Error in saving your data.Please try again";
				mav.addObject("message", message);
				mav.setViewName("it/manager/saveData");
				mav.addObject("username",principal.getName() );
				return mav;					
			}
		  } 
		}
		
		
		@RequestMapping(value = "it/manager/deleteitemployee/op1" ,method = RequestMethod.POST)
		public String deleteEmployeeGet(Model model,HttpServletRequest request, Principal principal)
		{
			model.addAttribute("username", principal.getName());
			return  ("it/manager/deleteitemployee");
		}
		
/*		@RequestMapping(value = "it/manager/deleteitemployee/op1" ,method = RequestMethod.POST)
		public String deleteEmployeeGet(Model model,HttpServletRequest request,Principal principal)
		{
			String name = principal.getName();
			model.addAttribute("username", name);
			return  ("it/manager/deleteitemployeeData");
		}*/
			
		
		@RequestMapping(value = "it/manager/deleteitemployee" ,method = RequestMethod.POST)
		public String deleteEmployeePost(Model model,HttpServletRequest request,Principal principal)
		{
			System.out.println("\n Inside delete empployee post controller");
			String message = null ,userName;
			String name = principal.getName();
			userName = request.getParameter("userNametext");
			int status;
			try{
				if(userName.equals(""))
					throw new InvalidActivityException();
				status = itmanager.getDeleteApprovalStatus(userName, "IT");
				if(status==1 )
				{					
					message = "Employee "+ userName+ " has been deleted after approval of corporate level manager";
					itmanager.deleteItEmployee(userName);					
				} else  if (status==0){
					message= "Employee "+ userName+ " delete request has not beeen approved by corporate level manager yet";											
				} else if (status==-1)
				{
					message= "Employee "+ userName+ " delete request has beeen sent for approval to corporate level manager";						
					itmanager.insertDeleteRequesttoCM(userName,"IT",false);
				}
				model.addAttribute("message", message);	
				model.addAttribute("username", name);
				return ("it/manager/saveData");
				
			} catch (Exception e) {
				if(e instanceof InvalidActivityException )
				{
					e.printStackTrace();		
					message = "Error occured in deleting employee .Please use valid username";
					model.addAttribute("message", message);
					model.addAttribute("username", name);
					return ("it/manager/saveData");
				} else {
				// TODO Auto-generated catch block
				e.printStackTrace();						
				message = "Error occured in sending delete request";
				model.addAttribute("message", message);	
				model.addAttribute("username", name);
				return ("it/manager/saveData");
				}
			 }		
		}
		
		
		@RequestMapping(value = "it/manager/transferemployee" ,method = RequestMethod.POST)
		public ModelAndView transferEmployeeGet(Model model,HttpServletRequest request,Principal principal)
		{								
			Map <String,String> department = new LinkedHashMap<String,String>();	
			String name = principal.getName();
			department.put("sales", "Sales department");
			department.put("TM", "Transaction Management department");
			department.put("HR", "Human Resource department");
			department.put("CM", "Company Managment department");
			model.addAttribute("departmentList", department);	
			model.addAttribute("username", name);
			
	
			Map <String,String> roleList = new LinkedHashMap<String,String>();			
			roleList.put("manager", "manager");
			roleList.put("employee", "employee");
			model.addAttribute("roleList", roleList);
			
			return new ModelAndView("it/manager/transferitemployee", "signupemployee", new User());
		}
		
		@RequestMapping(value = "it/manager/transferemployee/op1" ,method = RequestMethod.POST)
		public String transferItEmployee( User user,Model model,HttpServletRequest request,Principal principal)
		{
			System.out.println("\n Inside delete empployee post controller");
			String message="",department = null,username=null ;
			String roleToBeupdated =null;
			username=request.getParameter("userNametext");
									
			try{												
				message= "Employee "+ username+ " has been transfered";	
				System.out.println("Check: " + user.getDepartment() + "   " + user.getRole());
				if(user.getDepartment().equals("NONE") || user.getRole().equals("NONE"))
				{
					message="Oops!! You seem to be lost because of some Bad Operation. Please press the Home button to return to your mainpage or Logout.";
					model.addAttribute("message", message);
					model.addAttribute("username", principal.getName());
					return ("it/manager/saveData");	
				}	
				roleToBeupdated = itmanager.getRoleTobechanged(user.getDepartment(),user.getRole());
				itmanager.updateUserRole(roleToBeupdated,IDepartments.IT,user.getDepartment(),username ,principal.getName());
				model.addAttribute("message", message);
				model.addAttribute("username", principal.getName());
				return ("it/manager/saveData");								
				
			} catch (Exception e) {
				
				if(e instanceof InvalidActivityException )
				{
					e.printStackTrace();		
					message = "Error occured in transferring employee .Please use valid username";
					model.addAttribute("message", message);			
					model.addAttribute("username", principal.getName());
					return ("it/manager/saveData");
				} else {
				// TODO Auto-generated catch block
				e.printStackTrace();						
				message = "Error occured in sending transfer request";
				model.addAttribute("message", message);				
				model.addAttribute("username", principal.getName());
				return ("it/manager/saveData");
				}
			 }		
		}
		
		
}
