package asu.edu.sbs.web.hr;

import java.security.Principal;
import java.util.LinkedHashMap;
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
import asu.edu.sbs.domain.User;
import asu.edu.sbs.hr.service.HrDeptManager;

@Controller
@RequestMapping(value= "/hr/hrmanager")
public class HrManagerController {
	
	@Autowired
	HrDeptManager hrmanager;
	ModelAndView savedMav;
		
		@RequestMapping(value = "manager/op1", method = RequestMethod.GET)
		public String addnewHrEmployee(Locale locale, Model model,Principal principal) {
			System.out.println("Inside hr manager Controller .............");
			model.addAttribute("username", principal.getName());
			return "hr/manager/manager";
		} 
		
		
		@RequestMapping(value = "hr/manager", method = RequestMethod.POST)
		public String addnewHrEmployeePost(Locale locale, Model model,Principal principal) {
			System.out.println("Inside hr manager post Controller .............");	
			model.addAttribute("username", principal.getName());
			return "hr/manager/hrmanager";
		}
		
		
		@RequestMapping(value = "/newhremployee", method = RequestMethod.POST)
		public ModelAndView newHrEmployeeGet(Locale locale, Model model,Principal principal) {
			System.out.println("Inside hr manager get Controller .............");							
			savedMav = new ModelAndView("hr/newhremployee", "signupemployee", new SignUpEmployee());
			model.addAttribute("username", principal.getName());
			return savedMav;
		}
		
		@RequestMapping(value = "/newhremployee/op1", method = RequestMethod.POST)
		public ModelAndView newHrEmployeePost(@ModelAttribute @Valid User user, BindingResult result, final RedirectAttributes attributes,Principal principal) {
			System.out.println("INSIDE hr manager post Controller .............");
			
			String message ;
			ModelAndView mav = new ModelAndView();
			try{				
				System.out.println("\n Inside Employee signup post controller");
				if(result.hasErrors())
				{
					//return new ModelAndView("hr/newhremployee", "signupemployee",employee);
					//return savedMav;
					return new ModelAndView("hr/manager/manager","signupemployee",user);
				}		 
						
				mav.setViewName("signup/saveData");
				message= "Your request has been submitted for approval";
				user.setDepartment("HR");
				user.setRole("ROLE_HR_EMPLOYEE");				
				hrmanager.insertValidUser(user,"admin",principal.getName());
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
		}

		
		@RequestMapping(value = "/deletehremployee/op1" ,method = RequestMethod.POST)
		public String deleteEmployeeGet(Model model,HttpServletRequest request, Principal principal)
		{
			model.addAttribute("username", principal.getName());
			return  ("hr/deletehremployee");
		}
			
		
		@RequestMapping(value = "/deletehremployee" ,method = RequestMethod.POST)
		public String deleteEmployeePost(Model model,HttpServletRequest request,Principal principal)
		{
			System.out.println("\n Inside delete empployee post controller");
			String message = null ,userName;
			userName = request.getParameter("userNametext");
			int status;
			try{				
				status = hrmanager.getDeleteApprovalStatus(userName, "HR");
				if(status==1 )
				{					
					message = "Employee "+ userName+ " has been deleted after approval of corporate level manager";
					hrmanager.deleteHrEmployee(userName);					
				} else  if (status==0){
					message= "Employee "+ userName+ " delete request has not beeen approved by corporate level manager yet";											
				} else if (status==-1)
				{
					message= "Employee "+ userName+ " delete request has beeen sent for approval to corporate level manager";						
					hrmanager.insertDeleteRequesttoCM(userName,"HR",false);
				}
				model.addAttribute("message", message);			
				model.addAttribute("username", principal.getName());
				return ("signup/saveData");
				
			} catch (Exception e) {
				if(e instanceof InvalidActivityException )
				{
					e.printStackTrace();		
					message = "Error occured in deleting employee .Please use valid username";
					model.addAttribute("message", message);			
					model.addAttribute("username", principal.getName());
					return ("signup/saveData");
				} else {
				// TODO Auto-generated catch block
				e.printStackTrace();						
				message = "Error occured in sending delete request";
				model.addAttribute("message", message);				
				model.addAttribute("username", principal.getName());
				return ("signup/saveData");
				}
			 }		
		}
		
		
		@RequestMapping(value = "/transferemployee" ,method = RequestMethod.POST)
		public ModelAndView transferEmployeeGet(Model model,HttpServletRequest request,Principal principal)
		{								
			Map <String,String> department = new LinkedHashMap<String,String>();			
			department.put("sales", "Sales department");
			department.put("TM", "Transaction Management department");
			department.put("IT", "IT & Tech Support department");
			department.put("CM", "Company Managment department");
			model.addAttribute("departmentList", department);		
			model.addAttribute("username", principal.getName());
			return new ModelAndView("hr/transferhremployee", "signupemployee", new SignUpEmployee());
		}
		
		@RequestMapping(value = "/transferemployee/op1" ,method = RequestMethod.POST)
		public String transferHrEmployee( SignUpEmployee employee,Model model,HttpServletRequest request,Principal principal)
		{
			System.out.println("\n Inside delete empployee post controller");
			String message,department = null ;
									
			try{												
				message= "Employee "+ request.getParameter("userNametext")+ " has been transfered";					
				hrmanager.updateDepartmentOfEmployee(request.getParameter("userNametext"), employee.getDepartment());
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
