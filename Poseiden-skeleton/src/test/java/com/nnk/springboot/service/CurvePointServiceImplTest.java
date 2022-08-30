package com.nnk.springboot.service;

import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurvePointServiceImplTest {

    private CurvePointServiceCrudImpl curvePointService;

    @Mock
    private CurvePointRepository curvePointRepository;

    @BeforeEach
    void init() {
        curvePointService = new CurvePointServiceCrudImpl(curvePointRepository);
    }

    @Test
    void addCurvePoint() {
        CurvePoint curvePoint = new CurvePoint(1, 21, null, 4.3, 20.4, null);

        when(curvePointRepository.save(curvePoint)).thenAnswer(c -> c.getArguments()[0]);

        CurvePoint curvePointToAdd = curvePointService.add(curvePoint);

        assertThat(curvePointToAdd)
                .satisfies(cp -> {
                    assertThat(cp.getId()).hasToString("1");
                    assertThat(cp.getCurveId()).hasToString("21");
                    assertThat(cp.getTerm()).hasToString("4.3");
                    assertThat(cp.getCurveValue()).hasToString("20.4");
                });
    }

    @Test
    void deleteCurvePoint() {
        //GIVEN
        CurvePoint curvePoint = new CurvePoint(1, 21, null, 4.3, 20.4, null);

        //WHEN
        curvePointService = Mockito.spy(new CurvePointServiceCrudImpl(curvePointRepository));
        curvePointService.delete(curvePoint.getId());

        //THEN
        verify(curvePointRepository, times(1)).deleteById(curvePoint.getId());
    }


    @Test
    void getCurvePointById() {
        //GIVEN
        CurvePoint curvePoint = new CurvePoint(1, 21, null, 4.3, 20.4, null);

        //WHEN
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
        CurvePoint curvePointToGet = curvePointService.getById(curvePoint.getId());

        //THEN
        Assertions.assertEquals(1, curvePointToGet.getId());
    }

    @Test
    void getCurvePointByIdNotExistException() {
        int curvePointNotExisting = 99;
        Assertions.assertThrows(ResourceNotExistException.class, () -> curvePointService.getById(curvePointNotExisting));
    }

    @Test
    void getAllCurvePoint() {
        //GIVEN
        List<CurvePoint> allCurvePoint = List.of(
                new CurvePoint(1, 21, null, 4.3, 20.4, null),
                new CurvePoint(2, 21, null, 4.3, 20.4, null)
        );

        //WHEN
        when(curvePointRepository.findAll()).thenReturn(allCurvePoint);

        List<CurvePoint> curvePointList = curvePointService.getAll();

        //THEN
        Assertions.assertEquals(2, curvePointList.size());
    }

    @Test
    void updateCurvePoint() {
        //GIVEN
        CurvePoint curvePoint = new CurvePoint(1, 21, null, 4.3, 20.4, null);
        //WHEN
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        CurvePoint curvePointDto = new CurvePoint(34.5, 29.4);

        CurvePoint curvePointUpdated = curvePointService.update(1, curvePointDto);

        //THEN
        Assertions.assertEquals(34.5, curvePointUpdated.getTerm());
        Assertions.assertEquals(29.4, curvePointUpdated.getCurveValue());

    }

    @Test
    void updateBidListNotExistingException() {
        CurvePoint curvePointDto = new CurvePoint(1, 21, null, 4.3, 20.4, null);
        Assertions.assertThrows(ResourceNotExistException.class, () -> curvePointService.update(1, curvePointDto));
    }

}