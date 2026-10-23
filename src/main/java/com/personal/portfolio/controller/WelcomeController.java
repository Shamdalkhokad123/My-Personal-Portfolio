package com.personal.portfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	
	@GetMapping("/")
	public String overview(Model model) {
		model.addAttribute("activePage", "overview");
		return "overview";
	}

	@GetMapping("/experience")
	public String experience(Model model) {
		model.addAttribute("activePage", "experience");
		return "experience";
	}

	@GetMapping("/projects")
	public String projects(Model model) {
		model.addAttribute("activePage", "projects");
		return "projects";
	}

	@GetMapping("/skills")
	public String skills(Model model) {
		model.addAttribute("activePage", "skills");
		return "skills";
	}

	@GetMapping("/contact")
	public String contact(Model model) {
		model.addAttribute("activePage", "contact");
		return "contact";
	}

	@GetMapping({"/jobs", "/radar"})
	public String jobsDashboard(Model model) {
		model.addAttribute("activePage", "jobs");
		return "jobsDashboard";
	}

	@GetMapping("/all")
	public String welcomeCustom(Model model) {
		model.addAttribute("activePage", "all");
		return "welcomeCustom";
	}

	@GetMapping("/hello")
	public String welcome() {
		return "welcome";
	}
}
