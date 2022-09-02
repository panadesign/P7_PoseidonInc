package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.UserAccount;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HomeController
{
	@RequestMapping("/")
	public String home(Model model,  UserAccount userAccount)
	{
		return "home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/bidList/list";
	}
	
}
