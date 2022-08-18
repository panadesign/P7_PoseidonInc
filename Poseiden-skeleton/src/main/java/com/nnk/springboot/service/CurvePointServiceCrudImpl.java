package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.ResourceExistException;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.repositories.RatingRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class CurvePointServiceCrudImpl extends AbstractServiceCrud<CurvePoint, CurvePointRepository>{

	public CurvePointServiceCrudImpl(CurvePointRepository repository) {
		super(repository);
	}
	
	@Override
	public CurvePoint add(CurvePoint curvePoint) {
		if(repository.findById(curvePoint.getId()).isPresent()) {
			throw new ResourceExistException("This curve point is already existing: " + curvePoint.getId());
		}
		CurvePoint newCurvePoint = new CurvePoint(curvePoint.getId(), curvePoint.getCurveId(), curvePoint.getAsOfDate(), curvePoint.getTerm(), curvePoint.getValue(), curvePoint.getCreationDate());
		log.debug("A new curve point has been created: " + curvePoint.getId());
		return repository.save(newCurvePoint);
	}

	@Override
	public CurvePoint update(Integer id, CurvePoint curvePointDto) {
		CurvePoint curvePointToUpdate = repository.findById(id)
				.orElseThrow(() -> new ResourceNotExistException("Curve point with id " + id + " doesn't exist."));

		CurvePoint updatedCurvePoint = curvePointToUpdate.update(curvePointDto);
		repository.save(updatedCurvePoint);
		return updatedCurvePoint;
	}

}
