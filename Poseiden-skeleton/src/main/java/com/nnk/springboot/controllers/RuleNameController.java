package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.CrudService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@Log4j2
public class RuleNameController {
	@Qualifier("RuleName")
	private final CrudService<RuleName> crudService;
	@Autowired
	private RuleNameRepository ruleNameRepository;
	
	RuleNameController(CrudService<RuleName> crudService) {
		this.crudService = crudService;
	}
	
	@RequestMapping("/ruleName/list")
	public String home(Model model) {
		log.debug("Get all rule name");
		List<RuleName> allRuleName = crudService.getAll();
		model.addAttribute("allRuleName", allRuleName);
		return "ruleName/list";
	}
	
	@GetMapping("/ruleName/add")
	public String addRuleForm(RuleName bid) {
		return "ruleName/add";
	}
	
	@PostMapping("/ruleName/validate")
	public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
		if (result.hasErrors()) {
			log.error("Error: " + result.getFieldError());
			return "ruleName/add";
		}
		crudService.add(ruleName);
		model.addAttribute("ruleName", ruleNameRepository.findAll());
		return "redirect:/ruleName/list";
	}
	
	@GetMapping("/ruleName/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, RuleName ruleName, Model model) {
		log.debug("Get update form for id" + id);
		ruleName = crudService.getById(id);
		model.addAttribute("ruleName", ruleName);
		return "ruleName/update";
	}
	
	@PostMapping("/ruleName/update/{id}")
	public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
	                             BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "ruleName/update";
		}
		crudService.update(id, ruleName);
		model.addAttribute("ruleName", ruleNameRepository.findAll());
		return "redirect:/ruleName/list";
	}
	
	@GetMapping("/ruleName/delete/{id}")
	public String deleteRuleName(@PathVariable("id") Integer id) {
		log.debug("Delete rule name with id: " + id);
		crudService.delete(id);
		return "redirect:/ruleName/list";
	}
}
