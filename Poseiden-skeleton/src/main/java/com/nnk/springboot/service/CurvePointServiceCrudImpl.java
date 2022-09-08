package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.repositories.CurvePointRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * The type Curve point service crud.
 */
@Log4j2
@Component
public class CurvePointServiceCrudImpl extends AbstractServiceCrud<CurvePoint, CurvePointRepository>{

    /**
     * Instantiates a new Curve point service crud.
     *
     * @param repository the repository
     */
    public CurvePointServiceCrudImpl(CurvePointRepository repository) {
		super(repository);
	}
	
	@Override
	public CurvePoint add(CurvePoint curvePoint) {
		log.debug("A new curve point has been created: " + curvePoint.getId());
		return repository.save(curvePoint);
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
