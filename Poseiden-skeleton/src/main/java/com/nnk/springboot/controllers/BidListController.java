package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.CrudService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
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
public class BidListController {
	@Qualifier("bidList")
	private final CrudService<BidList> crudService;

	BidListController(CrudService<BidList> crudService) {
		this.crudService = crudService;
	}
	
	@Autowired
	private BidListRepository bidListRepository;

	@RequestMapping("/bidList/list")
	public String home(Model model) {
		log.debug("Get all bid list");
		List<BidList> allBidList = crudService.getAll();
		model.addAttribute("allBidList", allBidList);
		return "bidList/list";
	}

	@GetMapping("/bidList/add")
	public String addBidForm(BidList bidList) {
		log.debug("Get add bid list form");
		return "bidList/add";
	}

	@PostMapping("/bidList/validate")
	public String validate(@Valid BidList bidList, BindingResult result, Model model) {
		log.debug("Add a new bid");
		if(result.hasErrors()) {
			log.error("Error: " + result.getFieldError());
			return "bidList/add";
		}
		crudService.add(bidList);
		model.addAttribute("bidList", bidListRepository.findAll());
		log.debug("A new bid list has been created and bidList/validate redirect to bidList/list");
		return "redirect:/bidList/list";
	}

	@GetMapping("/bidList/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, BidList bidList, Model model) {
		log.debug("Get update form for id " + id);
		bidList = crudService.getById(id);
		model.addAttribute("bidList", bidList);
		return "bidList/update";
	}

	@PostMapping("/bidList/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,	BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "bidList/update";
		}
		crudService.update(id, bidList);
		model.addAttribute("bidList", bidListRepository.findAll());
		log.debug("Bid list with id "+ id + " has been updated and bidList/update/" + id + " is redirected to bidList/list");
		return "redirect:/bidList/list";
	}

	@GetMapping("/bidList/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id) {
		log.debug("Delete bid list with id" + id);
		BidList bidList = crudService.getById(id);
		crudService.delete(bidList.getId());
		log.debug("Bid list with id "+ id + " has been deleted and bidList/delete/" + id + " is redirected to bidList/list");
		return "redirect:/bidList/list";
	}
}
