package asu.edu.sbs.web.corporatemanager;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import asu.edu.sbs.exception.BankStorageException;
import asu.edu.sbs.hr.service.CorporateMutliAuthManager;

@Controller
public class CorporateMutliAuthController {

	@Autowired
	private CorporateMutliAuthManager corporateManager;
	

	@RequestMapping(value = "/corporate/allactivemanagers", method = RequestMethod.POST)
	public String getAllActiveManagers(Locale locale, Model model) throws BankStorageException {	
		model.addAttribute("managersList",corporateManager.getAllActiveManagers());
		return "corporate/allmanagers";
	}
	
	@RequestMapping(value = "/corporate/deactivate/{username}", method = RequestMethod.GET)
	public String deactivateManager(@PathVariable("username") String username, Locale locale, Model model) throws BankStorageException {	
		System.out.println("Inside deactivate....."+username);
		//TODO: pass on delete request
		//TODO: success or failure message
		
		model.addAttribute("managersList",corporateManager.getAllActiveManagers());
		return "redirect:/corporate";
	}
}
