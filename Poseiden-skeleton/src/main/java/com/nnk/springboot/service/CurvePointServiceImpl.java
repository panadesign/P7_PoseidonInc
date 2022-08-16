package com.nnk.springboot.service;

import com.nimbusds.jose.jwk.Curve;
import com.nnk.springboot.Exception.ResourceExistException;
import com.nnk.springboot.Exception.ResourceNotExistException;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.CurvePointRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Component
@Log4j2
public class CurvePointServiceImpl implements CurvePointService {

	private final CurvePointRepository curvePointRepository;

	CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
		this.curvePointRepository = curvePointRepository;
	}

	public CurvePoint addCurvePoint(CurvePoint curvePoint) {
		if(curvePointRepository.findById(curvePoint.getId()).isPresent()) {
			throw new ResourceExistException("This curve point is already existing: " + curvePoint.getId());
		}
		CurvePoint newCurvePoint = new CurvePoint(curvePoint.getId(), curvePoint.getCurveId(), curvePoint.getAsOfDate(), curvePoint.getTerm(), curvePoint.getValue(), curvePoint.getCreationDate());
		log.debug("A new curve point has been created: " + curvePoint.getId());
		return curvePointRepository.save(newCurvePoint);
	}

	public void deleteCurvePoint(CurvePoint curvePoint) {
		if(!curvePointRepository.findById(curvePoint.getId()).isPresent()) {
			throw new ResourceNotExistException("Curve point with id " + curvePoint.getId() + " doesn't exist");
		}
		log.debug("Curve point with id: " + curvePoint.getId() + " has been deleted");
		curvePointRepository.delete(curvePoint);
	}

	public CurvePoint getCurvePointById(CurvePoint curvePoint) {
		log.debug("Curve point with id: " + curvePoint.getId());
		return curvePointRepository.findById(curvePoint.getId())
				.orElseThrow(() -> new ResourceNotExistException("Curve point with id " + curvePoint.getId() + " doesn't exist"));
	}

	public List<CurvePoint> getAllCurvePoint() {
		log.debug("Get all curve points");
		return curvePointRepository.findAll();
	}
}
