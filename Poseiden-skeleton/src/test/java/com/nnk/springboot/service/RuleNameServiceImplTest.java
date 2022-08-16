package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RuleNameServiceImplTest {
	private RuleNameService ruleNameService;

	@Mock
	private RuleNameRepository ruleNameRepository;

	@BeforeEach
	void init() {
		ruleNameService = new RuleNameServiceImpl(ruleNameRepository);
	}
	@Test
	void addRuleName() {
		//GIVEN
		RuleName ruleName = new RuleName(1, "name", "description", "json", "template", "sqlStr", "sqlPart");
		//WHEN
		when(ruleNameRepository.save(ruleName)).thenAnswer(r -> r.getArguments()[0]);
		ruleNameService.addRuleName(ruleName);

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
}