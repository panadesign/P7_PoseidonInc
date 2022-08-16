package com.nnk.springboot.service;

import com.nnk.springboot.Exception.ResourceExistException;
import com.nnk.springboot.Exception.ResourceNotExistException;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.repositories.RuleNameRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
@Log4j2
public class RuleNameServiceImpl implements RuleNameService{

	private final RuleNameRepository ruleNameRepository;

	RuleNameServiceImpl(RuleNameRepository ruleNameRepository) {
		this.ruleNameRepository = ruleNameRepository;
	}

	public RuleName addRuleName(RuleName ruleName) {
		if(ruleNameRepository.findById(ruleName.getId()).isPresent()) {
			throw new ResourceExistException("This rule name is already existing: " + ruleName.getId());
		}

		RuleName newRuleName = new RuleName(ruleName.getId(), ruleName.getName(), ruleName.getDescription(), ruleName.getJson(), ruleName.getTemplate(), ruleName.getSqlStr(), ruleName.getSqlPart());
		log.debug("A new rule name has been created: " + ruleName.getId());
		return ruleNameRepository.save(newRuleName);
	}

}
