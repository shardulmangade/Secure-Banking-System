package asu.edu.sbs.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class BankExceptionHandler {

	
	private static final Logger logger = LoggerFactory.getLogger(BankExceptionHandler.class);
//
//	
//	@ExceptionHandler(QuadrigaStorageException.class)
//	public ModelAndView handleSQLException(QuadrigaStorageException ex) {
//		
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("auth/storageissue");
//		modelAndView.addObject("ex_name", ex.getClass().getName());
//		modelAndView.addObject("ex_message", ex.getMessage());
//		logger.error(ex.getMessage(), ex);
//		return modelAndView;
//	}
//	
//
//	@ExceptionHandler(value ={ QuadrigaAccessException.class})
//	public ModelAndView handleUserAccessException(QuadrigaAccessException ex) {
//		
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("auth/accessissue");
//		modelAndView.addObject("ex_name", ex.getClass().getName());
//		modelAndView.addObject("ex_message", ex.getMessage());
//		logger.error(ex.getMessage(), ex);
//		return modelAndView;
//	}
	
	}
