package com.nnk.springboot.controllers;

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

import javax.validation.Valid;
import java.util.List;

/**
 * The type Rating controller.
 */
@Controller
@Log4j2
public class RatingController {
	@Qualifier("Rating")
	private final CrudService<Rating> crudService;

    /**
     * Instantiates a new Rating controller.
     *
     * @param crudService the crud service
     */
    RatingController(CrudService<Rating> crudService) {
		this.crudService = crudService;
	}

    /**
     * The Rating repository.
     */
    @Autowired
	RatingRepository ratingRepository;

    /**
     * Home string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/rating/list")
	public String home(Model model) {
		log.debug("Get all ratings");
		List<Rating> allRatings = crudService.getAll();
		model.addAttribute("allRatings", allRatings);
		return "rating/list";
	}

    /**
     * Add rating form string.
     *
     * @param rating the rating
     * @return the string
     */
    @GetMapping("/rating/add")
	public String addRatingForm(Rating rating) {
		log.debug("Get add rating form");
		return "rating/add";
	}

    /**
     * Validate string.
     *
     * @param rating the rating
     * @param result the result
     * @param model  the model
     * @return the string
     */
    @PostMapping("/rating/validate")
	public String validate(@Valid Rating rating, BindingResult result, Model model) {
		log.debug("Add a new rating");
		if (result.hasErrors()) {
			log.error("Error: " + result.getFieldError());
			return "rating/add";
		}
		
		crudService.add(rating);
		log.debug("rating with id: " + rating.getId() + ", has been added");
		model.addAttribute("rating", ratingRepository.findAll());
		log.debug("A new rating has been created and rating/validate redirect to rating/list");
		return "redirect:/rating/list";
	}

    /**
     * Show update form string.
     *
     * @param id     the id
     * @param rating the rating
     * @param model  the model
     * @return the string
     */
    @GetMapping("/rating/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Rating rating, Model model) {
		log.debug("Get update form for id " + id);
		Rating rate = crudService.getById(id);
		model.addAttribute("rating", rate);
		return "rating/update";
	}

    /**
     * Update rating string.
     *
     * @param id     the id
     * @param rating the rating
     * @param result the result
     * @param model  the model
     * @return the string
     */
    @PostMapping("/rating/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "rating/update";
		}
		crudService.update(id, rating);
		model.addAttribute("rating", ratingRepository.findAll());
		log.debug("Rating with id "+ rating.getId() + " has been updated and rating/update/"+rating.getId() + " is redirected to rating/list");
		return "redirect:/rating/list";
	}

    /**
     * Delete rating string.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/rating/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) {
		Rating ratingToDelete = crudService.getById(id);
		log.debug("Get rating to delete" + ratingToDelete.getId());
		crudService.delete(ratingToDelete.getId());
		log.debug("Rating with id "+ id + " has been deleted and rating/delete/" + id + " is redirected to rating/list");
		return "redirect:/rating/list";
	}
}
