package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Rating repository.
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
	Optional<Rating> findById(Integer id);

}
