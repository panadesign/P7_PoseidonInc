package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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
public class RatingController {
	@Qualifier("Rating")
	private final CrudService<Rating> crudService;
	
	RatingController(CrudService<Rating> crudService) {
		this.crudService = crudService;
	}
	
	@Autowired
	RatingRepository ratingRepository;
	
	@RequestMapping("/rating/list")
	public String home(Model model) {
		log.debug("Get all ratings");
		List<Rating> allRatings = crudService.getAll();
		model.addAttribute("allRatings", allRatings);
		return "rating/list";
	}
	
	@GetMapping("/rating/add")
	public String addRatingForm(Rating rating) {
		log.debug("Get add rating form");
		return "rating/add";
	}
	
	@PostMapping("/rating/validate")
	public String validate(@Valid Rating rating, BindingResult result, Model model) {
		log.debug("Add a new rating with id: " + rating.getId());
		if (result.hasErrors()) {
			log.error("Error: " + result.getFieldError());
			return "rating/add";
		}
		
		crudService.add(rating);
		log.debug("rating with id: " + rating.getId() + ", has been added");
		model.addAttribute("rating", ratingRepository.findAll());
		return "redirect:/rating/list";
	}
	
	@GetMapping("/rating/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Rating rating, Model model) {
		log.debug("Get update form for id" + id);
		Rating rate = crudService.getById(id);
		model.addAttribute("rating", rate);
		return "rating/update";
	}

	@PostMapping("/rating/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "rating/update/{id}";
		}
		crudService.update(id, rating);
		model.addAttribute("rating", ratingRepository.findAll());
		return "redirect:/rating/list";
	}
	
	@GetMapping("/rating/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) {
		Rating ratingToDelete = crudService.getById(id);
		log.debug("Get rating to delete" + ratingToDelete.getId());
		crudService.delete(ratingToDelete.getId());
		return "redirect:/rating/list";
	}
}
