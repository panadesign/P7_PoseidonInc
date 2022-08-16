package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exception.ResourceExistException;
import com.nnk.springboot.repositories.RuleNameRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RuleNameServiceCrudImpl extends AbstractServiceCrud<RuleName, RuleNameRepository>{

	public RuleNameServiceCrudImpl(RuleNameRepository repository) {
		super(repository);
	}
	
	@Override
	public RuleName add(RuleName ruleName) {
		if(repository.findById(ruleName.getId()).isPresent()) {
			throw new ResourceExistException("This rule name is already existing: " + ruleName.getId());
		}

		RuleName newRuleName = new RuleName(ruleName.getId(), ruleName.getName(), ruleName.getDescription(), ruleName.getJson(), ruleName.getTemplate(), ruleName.getSqlStr(), ruleName.getSqlPart());
		log.debug("A new rule name has been created: " + ruleName.getId());
		return repository.save(newRuleName);
	}

	@Override
	public RuleName update(Integer id, RuleName ruleName) {
		return null;
	}

}
