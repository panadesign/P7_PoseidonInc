package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The type Home controller.
 */
@Controller
public class HomeController
{
    /**
     * Home string.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping("/")
	public String home(Model model)
	{
		return "home";
	}

    /**
     * Admin home string.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/bidList/list";
	}
	
}
