package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeController {
	@Qualifier("Trade")
	private final CrudService<Trade> crudService;
	@Autowired
	private TradeRepository tradeRepository;
	
	TradeController(CrudService<Trade> crudService) {
		this.crudService = crudService;
	}
	
	@RequestMapping("/trade/list")
	public String home(Model model) {
		log.debug("Get all trades");
		List<Trade> allTrades = crudService.getAll();
		model.addAttribute("allTrades", allTrades);
		return "trade/list";
	}
	
	@GetMapping("/trade/add")
	public String addUser(Trade trade) {
		return "trade/add";
	}
	
	@PostMapping("/trade/validate")
	public String validate(@Valid Trade trade, BindingResult result, Model model) {
		log.debug("Add a new trade");
        if (result.hasErrors()) {
            log.error("Error: " + result.getFieldError());
            return "trade/add";
        }
        crudService.add(trade);
        model.addAttribute("allTrades", tradeRepository.findAll());
		return "redirect:/trade/list";
	}
	
	@GetMapping("/trade/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Trade trade, Model model) {
        log.debug("Get update form for id" + id);
        trade = crudService.getById(id);
        model.addAttribute("trade", trade);
		return "trade/update";
	}
	
	@PostMapping("/trade/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
	                          BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        }
        crudService.update(id, trade);
        model.addAttribute("trade", tradeRepository.findAll());
		return "redirect:/trade/list";
	}
	
	@GetMapping("/trade/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) {
		log.debug("Delete trade with id: " + id);
		crudService.delete(id);
		return "redirect:/trade/list";
	}
}
