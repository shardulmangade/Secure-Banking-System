package asu.edu.sbs.web.it;

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
import asu.edu.sbs.it.service.ItDeptManager;

@Controller
public class ItManagerController {
	
	@Autowired
	ItDeptManager itmanager;
	ModelAndView savedMav;
		
		@RequestMapping(value = "it/manager", method = RequestMethod.GET)
		public String ItManager(Locale locale, Model model) {
			System.out.println("Inside it manager Controller .............");				
			return "it/manager/manager";
		} 
		
		
		@RequestMapping(value = "it/manager/deleteitemployee/op1" ,method = RequestMethod.POST)
		public String deleteEmployeeGet(Model model,HttpServletRequest request)
		{
			return  ("it/manager/deleteitemployee");
		}
			
		
		@RequestMapping(value = "it/manager/deleteitemployee" ,method = RequestMethod.POST)
		public String deleteEmployeePost(Model model,HttpServletRequest request)
		{
			System.out.println("\n Inside delete empployee post controller");
			String message = null ,userName;
			userName = request.getParameter("userNametext");
			int status;
			try{				
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
				return ("it/manager/saveData");
				
			} catch (Exception e) {
				if(e instanceof InvalidActivityException )
				{
					e.printStackTrace();		
					message = "Error occured in deleting employee .Please use valid username";
					model.addAttribute("message", message);							
					return ("it/manager/saveData");
				} else {
				// TODO Auto-generated catch block
				e.printStackTrace();						
				message = "Error occured in sending delete request";
				model.addAttribute("message", message);				
				return ("it/manager/saveData");
				}
			 }		
		}
		
		
		@RequestMapping(value = "it/manager/transferemployee" ,method = RequestMethod.POST)
		public ModelAndView transferEmployeeGet(Model model,HttpServletRequest request)
		{								
			Map <String,String> department = new LinkedHashMap<String,String>();			
			department.put("sales", "Sales department");
			department.put("TM", "Transaction Management department");
			department.put("HR", "Human Resource department");
			department.put("CM", "Company Managment department");
			model.addAttribute("departmentList", department);			
			return new ModelAndView("it/manager/transferitemployee", "signupemployee", new SignUpEmployee());
		}
		
		@RequestMapping(value = "it/manager/transferemployee/op1" ,method = RequestMethod.POST)
		public String transferItEmployee( SignUpEmployee employee,Model model,HttpServletRequest request)
		{
			System.out.println("\n Inside delete empployee post controller");
			String message,department = null ;
									
			try{												
				message= "Employee "+ request.getParameter("userNametext")+ " has been transfered";					
				itmanager.updateDepartmentOfEmployee(request.getParameter("userNametext"), employee.getDepartment());
				model.addAttribute("message", message);							
				return ("it/manager/saveData");
				
			} catch (Exception e) {
				
				if(e instanceof InvalidActivityException )
				{
					e.printStackTrace();		
					message = "Error occured in transferring employee .Please use valid username";
					model.addAttribute("message", message);							
					return ("it/manager/saveData");
				} else {
				// TODO Auto-generated catch block
				e.printStackTrace();						
				message = "Error occured in sending transfer request";
				model.addAttribute("message", message);				
				return ("it/manager/saveData");
				}
			 }		
		}
		
		
}
