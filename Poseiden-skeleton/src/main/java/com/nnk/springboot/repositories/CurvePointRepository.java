package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Curve point repository.
 */
@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {
	Optional<CurvePoint> findById(Integer id);
}
