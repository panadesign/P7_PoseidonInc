package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * The interface Rule name repository.
 */
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}
