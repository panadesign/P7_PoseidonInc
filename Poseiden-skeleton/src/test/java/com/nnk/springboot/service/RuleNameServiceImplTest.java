package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exception.ResourceExistException;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RuleNameServiceImplTest {
	private RuleNameServiceCrudImpl ruleNameService;

	@Mock
	private RuleNameRepository ruleNameRepository;

	@BeforeEach
	void init() {
		ruleNameService = new RuleNameServiceCrudImpl(ruleNameRepository);
	}
	@Test
	void addRuleName() {
		//GIVEN
		RuleName ruleName = new RuleName(1, "name", "description", "json", "template", "sqlStr", "sqlPart");
		//WHEN
		when(ruleNameRepository.save(ruleName)).thenAnswer(r -> r.getArguments()[0]);
		ruleNameService.add(ruleName);

		//THEN
		assertThat(ruleName)
				.satisfies(ruleName1 -> {
					assertThat(ruleName1.getId()).hasToString("1");
					assertThat(ruleName1.getName()).hasToString("name");
					assertThat(ruleName1.getDescription()).hasToString("description");
					assertThat(ruleName1.getJson()).hasToString("json");
					assertThat(ruleName1.getTemplate()).hasToString("template");
					assertThat(ruleName1.getSqlStr()).hasToString("sqlStr");
					assertThat(ruleName1.getSqlPart()).hasToString("sqlPart");
				});
	}

	@Test
	void deleteRuleName() {
		//GIVEN
		RuleName ruleName = new RuleName(1, "name", "description", "json", "template", "sqlStr", "sqlPart");

		//WHEN
		ruleNameService.delete(ruleName.getId());

		//THEN
		verify(ruleNameRepository, times(1)).deleteById(ruleName.getId());
	}

	@Test
	void getAllRuleName() {
		//GIVEN
		RuleName ruleName = new RuleName(1, "name", "description", "json", "template", "sqlStr", "sqlPart");
		RuleName ruleName2 = new RuleName(1, "name", "description", "json", "template", "sqlStr", "sqlPart");

		List<RuleName> allRuleName = ruleNameService.getAll();

		allRuleName.add(ruleName);
		allRuleName.add(ruleName2);
		when(ruleNameRepository.findAll()).thenReturn(allRuleName);

		List<RuleName> ruleNameList = ruleNameService.getAll();

		Assertions.assertEquals(2, ruleNameList.size());
	}

	@Test
	void getRuleNameById() {
		//GIVEN
		RuleName ruleName = new RuleName(1, "name", "description", "json", "template", "sqlStr", "sqlPart");

		//WHEN
		when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

		RuleName getRuleName = ruleNameService.getById(ruleName.getId());

		//THEN
		Assertions.assertEquals(1, getRuleName.getId());
	}

	@Test
	void getRuleNameByIdNotExistResource() {
		RuleName ruleName = new RuleName(1, "name", "description", "json", "template", "sqlStr", "sqlPart");

		Assertions.assertThrows(ResourceNotExistException.class, () -> ruleNameService.getById(ruleName.getId()));
	}

	@Test
	void updateRuleName() {
		RuleName ruleName = new RuleName(1, "name", "description", "json", "template", "sqlStr", "sqlPart");

		when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

		RuleName ruleNameDto = new RuleName("name1", "description1", "json1", "template1", "sqlStr1", "sqlPart1");
		RuleName ruleNameUpdated = ruleNameService.update(1, ruleNameDto);

		Assertions.assertEquals("name1", ruleNameUpdated.getName());
		Assertions.assertEquals("description1", ruleNameUpdated.getDescription());
		Assertions.assertEquals("json1", ruleNameUpdated.getJson());
		Assertions.assertEquals("template1", ruleNameUpdated.getTemplate());
		Assertions.assertEquals("sqlStr1", ruleNameUpdated.getSqlStr());
		Assertions.assertEquals("sqlPart1", ruleNameUpdated.getSqlPart());

	}

	@Test
	void updateRuleNameNotExistingException() {
		RuleName ruleNameDto = new RuleName("name1", "description1", "json1", "template1", "sqlStr1", "sqlPart1");
		Assertions.assertThrows(ResourceNotExistException.class, () -> ruleNameService.update(1, ruleNameDto));
	}
}