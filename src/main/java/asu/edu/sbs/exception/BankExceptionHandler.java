package asu.edu.sbs.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class BankExceptionHandler {

	
	private static final Logger logger = LoggerFactory.getLogger(BankExceptionHandler.class);

	@ExceptionHandler(value ={ BankStorageException.class})
	public ModelAndView handleDatabaseException(BankStorageException ex) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("exceptions/storageissue");
		modelAndView.addObject("ex_name", ex.getClass().getName());
		modelAndView.addObject("ex_message", ex.getMessage());
//		logger.error(ex.getMessage(), ex);
		return modelAndView;
	}
	
	@ExceptionHandler(value ={ Exception.class})
	public ModelAndView handleGeneralException(Exception ex) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("exceptions/storageissue");
		modelAndView.addObject("ex_name", ex.getClass().getName());
		modelAndView.addObject("ex_message", "Looks like someone is trying to do hack into our system. We have placed a temporary hold on the system. Please check back again after few minutes...");
//		logger.error(ex.getMessage(), ex);
		return modelAndView;
	}

	@ExceptionHandler(value ={ BankAccessException.class})
	public ModelAndView handleUserAccessException(BankAccessException ex) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("exceptions/accessissue");
		modelAndView.addObject("ex_name", ex.getClass().getName());
		modelAndView.addObject("ex_message", ex.getMessage());
//		logger.error(ex.getMessage(), ex);
		return modelAndView;
	}
	
	@ExceptionHandler(value ={ BankDeactivatedException.class})
	public ModelAndView handleUserDeactivateException(BankDeactivatedException ex) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("exceptions/accountdeactivated");
		modelAndView.addObject("ex_name", ex.getClass().getName());
		modelAndView.addObject("ex_message", ex.getMessage());
//		logger.error(ex.getMessage(), ex);
		return modelAndView;
	}
	
	}
