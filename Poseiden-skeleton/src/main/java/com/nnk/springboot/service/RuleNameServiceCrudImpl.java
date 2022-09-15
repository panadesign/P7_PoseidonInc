package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.repositories.RuleNameRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * The type Rule name service crud.
 */
@Log4j2
@Component
public class RuleNameServiceCrudImpl extends AbstractServiceCrud<RuleName, RuleNameRepository>{

    /**
     * Instantiates a new Rule name service crud.
     *
     * @param repository the repository
     */
    public RuleNameServiceCrudImpl(RuleNameRepository repository) {
		super(repository);
	}
	
	@Override
	public RuleName add(RuleName ruleName) {
		log.debug("A new rule name has been created: " + ruleName.getId());
		return repository.save(ruleName);
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
