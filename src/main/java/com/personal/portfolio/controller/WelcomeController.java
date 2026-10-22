package com.personal.portfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class WelcomeController {
	
	//@ResponseBody
	@GetMapping("/hello")
	public String welcome() {
		return "welcome";
	}
	
	@GetMapping("/")
	public String welcomeCustom() {
		return "welcomeCustom";
	}

	@GetMapping("/jobs")
	public String jobsDashboard() {
		return "jobsDashboard";
	}
	

}
