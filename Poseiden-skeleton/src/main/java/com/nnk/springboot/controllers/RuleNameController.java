package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
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

import javax.validation.Valid;
import java.util.List;

/**
 * The type Rule name controller.
 */
@Controller
@Log4j2
public class RuleNameController {
	@Qualifier("RuleName")
	private final CrudService<RuleName> crudService;
	@Autowired
	private RuleNameRepository ruleNameRepository;

    /**
     * Instantiates a new Rule name controller.
     *
     * @param crudService the crud service
     */
    RuleNameController(CrudService<RuleName> crudService) {
		this.crudService = crudService;
	}

    /**
     * Home string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/ruleName/list")
	public String home(Model model) {
		log.debug("Get all rule name");
		List<RuleName> allRuleName = crudService.getAll();
		model.addAttribute("allRuleName", allRuleName);
		return "ruleName/list";
	}

    /**
     * Add rule form string.
     *
     * @param ruleName the rule name
     * @return the string
     */
    @GetMapping("/ruleName/add")
	public String addRuleForm(RuleName ruleName) {
		log.debug("Get rule name account form");
		return "ruleName/add";
	}

    /**
     * Validate string.
     *
     * @param ruleName the rule name
     * @param result   the result
     * @param model    the model
     * @return the string
     */
    @PostMapping("/ruleName/validate")
	public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
		log.debug("Add a new rule name");
		if (result.hasErrors()) {
			log.error("Error: " + result.getFieldError());
			return "ruleName/add";
		}
		crudService.add(ruleName);
		model.addAttribute("ruleName", ruleNameRepository.findAll());
		log.debug("A new rule name has been created and ruleName/validate redirect to ruleName/list");
		return "redirect:/ruleName/list";
	}

    /**
     * Show update form string.
     *
     * @param id       the id
     * @param ruleName the rule name
     * @param model    the model
     * @return the string
     */
    @GetMapping("/ruleName/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, RuleName ruleName, Model model) {
		log.debug("Get update form for id " + id);
		ruleName = crudService.getById(id);
		model.addAttribute("ruleName", ruleName);
		return "ruleName/update";
	}

    /**
     * Update rule name string.
     *
     * @param id       the id
     * @param ruleName the rule name
     * @param result   the result
     * @param model    the model
     * @return the string
     */
    @PostMapping("/ruleName/update/{id}")
	public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
	                             BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "ruleName/update";
		}
		crudService.update(id, ruleName);
		model.addAttribute("ruleName", ruleNameRepository.findAll());
		log.debug("Rule name with id "+ ruleName.getId() + " has been updated and ruleName/update/"+ruleName.getId() + " is redirected to ruleName/list");
		return "redirect:/ruleName/list";
	}

    /**
     * Delete rule name string.
     *
     * @param id the id
     * @return the string
     */
    @GetMapping("/ruleName/delete/{id}")
	public String deleteRuleName(@PathVariable("id") Integer id) {
		log.debug("Delete rule name with id: " + id);
		crudService.delete(id);
		log.debug("Rule name with id "+ id + " has been deleted and ruleName/delete/" + id + " is redirected to ruleName/list");
		return "redirect:/ruleName/list";
	}
}
