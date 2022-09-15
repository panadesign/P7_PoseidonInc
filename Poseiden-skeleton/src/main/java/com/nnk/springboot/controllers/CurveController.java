package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
 * The type Curve controller.
 */
@Controller
@Log4j2
public class CurveController {
	@Qualifier("curvePoint")
	private final CrudService<CurvePoint> crudService;
	@Autowired
	private CurvePointRepository curvePointRepository;

    /**
     * Instantiates a new Curve controller.
     *
     * @param crudService the crud service
     */
    CurveController(CrudService<CurvePoint> crudService) {
		this.crudService = crudService;
	}

    /**
     * Home string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/curvePoint/list")
	public String home(Model model) {
		log.debug("Get all curve point");
		List<CurvePoint> curvePointList = crudService.getAll();
		model.addAttribute("curvePointList", curvePointList);
		return "curvePoint/list";
	}

    /**
     * Add bid form string.
     *
     * @param curvePoint the curve point
     * @return the string
     */
    @GetMapping("/curvePoint/add")
	public String addBidForm(CurvePoint curvePoint) {
		log.debug("Get add curve point form");
		return "curvePoint/add";
	}

    /**
     * Validate string.
     *
     * @param curvePoint the curve point
     * @param result     the result
     * @param model      the model
     * @return the string
     */
    @PostMapping("/curvePoint/validate")
	public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
		log.debug("Add a new curve point");
		if (result.hasErrors()) {
			log.error("Error: " + result.getFieldError());
			log.debug("A new curve point has been created and curvePoint/validate redirect to curvePoint/list");
			return "curvePoint/add";
		}
		
		crudService.add(curvePoint);
		log.debug("Curve point with id: " + curvePoint.getId() + ", has been added");
		model.addAttribute("curvePoint", curvePointRepository.findAll());
		return "redirect:/curvePoint/list";
	}

    /**
     * Show update form string.
     *
     * @param id         the id
     * @param curvePoint the curve point
     * @param model      the model
     * @return the string
     */
    @GetMapping("/curvePoint/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, CurvePoint curvePoint, Model model) {
		log.debug("Get update form for id " + id);
		CurvePoint cp = crudService.getById(id);
		model.addAttribute("curvePoint", cp);
		return "curvePoint/update";
	}

    /**
     * Update curve point string.
     *
     * @param id         the id
     * @param curvePoint the curve point
     * @param result     the result
     * @param model      the model
     * @return the string
     */
    @PostMapping("/curvePoint/update/{id}")
	public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "curvePoint/update";
		}
		curvePoint.setId(curvePoint.getId());
		crudService.update(id, curvePoint);
		model.addAttribute("curvePoints", curvePointRepository.findAll());
		log.debug("Curve point with id "+ curvePoint.getId() + " has been updated and curvePoint/update/"+curvePoint.getId() + " is redirected to curvePoint/list");
		return "redirect:/curvePoint/list";
	}

    /**
     * Delete bid string.
     *
     * @param id the id
     * @return the string
     */
    @GetMapping("/curvePoint/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id) {
		log.debug("Delete curve point with id" + id);
		crudService.delete(id);
		log.debug("Curve point with id "+ id + " has been deleted and curvePoint/delete/" + id + " is redirected to curvePoint/list");
		return "redirect:/curvePoint/list";
	}
}
