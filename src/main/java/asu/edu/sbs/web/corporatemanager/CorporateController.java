package asu.edu.sbs.web.corporatemanager;


import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import asu.edu.sbs.web.trial.HomeController;

@Controller
public class CorporateController {

private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/corporate", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome to corporate page, locale is {}.", locale);
		
		return "corporate/corporate";
	}
	
	/**
	 * This method handles the rendering of main content 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/corporate/op1",  method = RequestMethod.POST)
	public String getTest1(Locale locale, Model model) {
		logger.info("Welcome to corporate page, locale is {}.", locale);
		
		return "corporate/add";
	}
	
	@RequestMapping(value="/corporate/op2",  method = RequestMethod.POST)
	public String getTest2(Locale locale, Model model) {
		logger.info("Welcome to corporate page, locale is {}.", locale);
		
		return "corporate/delete";
	}
	
	@RequestMapping(value="/corporate/op3",  method = RequestMethod.POST)
	public String getTest3(Locale locale, Model model) {
		logger.info("Welcome to corporate page, locale is {}.", locale);
		
		return "corporate/transfer";
	}
	
	
}
