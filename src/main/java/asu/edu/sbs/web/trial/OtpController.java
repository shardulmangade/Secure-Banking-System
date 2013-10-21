package asu.edu.sbs.web.trial;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import asu.edu.sbs.domain.User;
import asu.edu.sbs.service.OTPManage;

@Controller
public class OtpController {

	   @Autowired
       private OTPManage otp;
       
       @RequestMapping(value = "login/userrequests", method = RequestMethod.GET)
       public String getUserRequests(Locale locale, Model model) {
               System.out.println("OTP for login.............");
               
               List<User> userRequests = otp.getAllUserRequests();
               model.addAttribute("userRequests", userRequests);
               return "login/userrequests";
       }
}
