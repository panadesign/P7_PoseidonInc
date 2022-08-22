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
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@Log4j2
public class CurveController {
	@Qualifier("curvePoint")
	private final CrudService<CurvePoint> crudService;
	
	CurveController(CrudService<CurvePoint> crudService) {
		this.crudService = crudService;
	}
	
	@Autowired
	private CurvePointRepository curvePointRepository;
	
	@RequestMapping("/curvePoint/list")
	public String home(Model model) {
		log.debug("Get all curve point");
		List<CurvePoint> curvePointList = crudService.getAll();
		model.addAttribute("curvePointList", curvePointList);
		return "curvePoint/list";
	}
	
	@GetMapping("/curvePoint/add")
	public String addBidForm() {
		log.debug("Get add curve point form");
		return "curvePoint/add";
	}
	
	@PostMapping("/curvePoint/validate")
	public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
		// TODO: check data valid and save to db, after saving return Curve list
		log.debug("Add a new curve point id: " + curvePoint.getId());
		
		if (result.hasErrors()) {
			log.error("Error: " + result.getFieldError());
			return "curvePoint/add";
		}
		
		crudService.add(curvePoint);
		log.debug("Curve point with id: " + curvePoint.getId() + ", has been added");
		model.addAttribute("curvePoint",curvePointRepository.findAll());
		return "redirect:/curvePoint/list";
	}
	
	@GetMapping("/curvePoint/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		CurvePoint curvePoint = crudService.getById(id);
		model.addAttribute("curvePointToUpdate", curvePoint);
		return "curvePoint/update";
	}
	
	//	@PostMapping("/curvePoint/update/{id}")
//	public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
//							BindingResult result, Model model) {
//		// TODO: check required fields, if valid call service to update Curve and return Curve list
//		return "redirect:/curvePoint/list";
//	}
//
	@GetMapping("/curvePoint/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id) {
		log.debug("Delete curve point with id" + id);
		CurvePoint curvePointToDelete = crudService.getById(id);
		crudService.delete(curvePointToDelete.getId());
		return "redirect:/curvePoint/list";
	}
}
