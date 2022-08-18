package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exception.ResourceExistException;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.repositories.RuleNameRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
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
	public RuleName update(Integer id, RuleName ruleNameDto) {
		RuleName ruleNameToUpdate = repository.findById(id)
				.orElseThrow(() -> new ResourceNotExistException("Rule name with id " + id + " doesn't exist."));

		RuleName updatedRuleName = ruleNameToUpdate.update(ruleNameDto);
		repository.save(updatedRuleName);
		return updatedRuleName;
	}

}
