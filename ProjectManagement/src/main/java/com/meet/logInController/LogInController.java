package com.meet.logInController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogInController {
	
	
	
	@RequestMapping("/logIn")
	public String logIn() {
		return "/login/login";
	}
	
	@RequestMapping("/logOut")
	public String logOut() {
		return "/login/login";
	}
	
}
