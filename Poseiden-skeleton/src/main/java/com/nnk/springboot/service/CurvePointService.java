package com.nnk.springboot.service;

import com.nimbusds.jose.jwk.Curve;
import com.nnk.springboot.domain.CurvePoint;

import java.util.List;

public interface CurvePointService {
	CurvePoint addCurvePoint(CurvePoint curvePoint);
	void deleteCurvePoint(CurvePoint curvePoint);
	CurvePoint getCurvePointById(CurvePoint curvePoint);
	List<CurvePoint> getAllCurvePoint();
//	CurvePoint updateCurvePoint(CurvePoint curvePoint);
}
