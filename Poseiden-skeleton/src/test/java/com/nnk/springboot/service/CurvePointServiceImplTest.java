package com.nnk.springboot.service;

import com.nnk.springboot.Exception.ResourceExistException;
import com.nnk.springboot.Exception.ResourceNotExistException;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurvePointServiceImplTest {

	private CurvePointService curvePointService;

	@Mock
	private CurvePointRepository curvePointRepository;

	@BeforeEach
	void init() {
		curvePointService = new CurvePointServiceImpl(curvePointRepository);
	}

	@Test
	void addCurvePoint() {
		CurvePoint curvePoint = new CurvePoint(1, 21, null, 4.3, 20.4, null);

		when(curvePointRepository.save(curvePoint)).thenAnswer(c -> c.getArguments()[0]);

		CurvePoint curvePointToAdd = curvePointService.addCurvePoint(curvePoint);

		assertThat(curvePointToAdd)
				.satisfies(cp -> {
					assertThat(cp.getId()).hasToString("1");
					assertThat(cp.getCurveId()).hasToString("21");
					assertThat(cp.getTerm()).hasToString("4.3");
					assertThat(cp.getValue()).hasToString("20.4");
				});
	}

	@Test
	void addCurvePointAlreadyExistException() {
		//GIVEN
		CurvePoint curvePoint = new CurvePoint(1, 21, null, 4.3, 20.4, null);

		//WHEN
		when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

		//THEN
		Assertions.assertThrows(ResourceExistException.class, () -> curvePointService.addCurvePoint(curvePoint));
	}

	@Test
	void deleteCurvePoint() {
		//GIVEN
		CurvePoint curvePoint = new CurvePoint(1, 21, null, 4.3, 20.4, null);

		//WHEN
		when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
		curvePointService = Mockito.spy(new CurvePointServiceImpl(curvePointRepository));
		curvePointService.deleteCurvePoint(curvePoint);

		//THEN
		verify(curvePointRepository, times(1)).delete(curvePoint);
	}

	@Test
	void deleteCurvePointNotExistException() {
		CurvePoint curvePoint = new CurvePoint(1, 21, null, 4.3, 20.4, null);

		//THEN
		Assertions.assertThrows(ResourceNotExistException.class, () -> curvePointService.deleteCurvePoint(curvePoint));
	}

	@Test
	void getCurvePointById() {
		//GIVEN
		CurvePoint curvePoint = new CurvePoint(1, 21, null, 4.3, 20.4, null);

		//WHEN
		when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
		CurvePoint curvePointToGet = curvePointService.getCurvePointById(curvePoint);

		//THEN
		Assertions.assertEquals(1, curvePointToGet.getId());
	}

	@Test
	void getCurvePointByIdNotExistException() {
		CurvePoint curvePoint = new CurvePoint(1, 21, null, 4.3, 20.4, null);
		Assertions.assertThrows(ResourceNotExistException.class, () -> curvePointService.getCurvePointById(curvePoint));
	}

	@Test
	void getAllCurvePoint() {
		//GIVEN
		CurvePoint curvePoint = new CurvePoint(1, 21, null, 4.3, 20.4, null);
		CurvePoint curvePoint2 = new CurvePoint(2, 21, null, 4.3, 20.4, null);

		List<CurvePoint> allCurvePoint = curvePointService.getAllCurvePoint();

		allCurvePoint.add(curvePoint);
		allCurvePoint.add(curvePoint2);

		//WHEN
		when(curvePointRepository.findAll()).thenReturn(allCurvePoint);

		List<CurvePoint> curvePointList = curvePointService.getAllCurvePoint();

		//THEN
		Assertions.assertEquals(2, curvePointList.size());
	}
}