package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.util.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.constraints.NotBlank;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
class RuleNameControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Autowired
    private Mapper mapper;

    private RuleName ruleName;


    @BeforeEach
    public void init() {
        ruleName = new RuleName("name", "description", "json", "template","sqlStr", "sqlPart");

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getRuleNameTest() throws Exception {
        //GIVEN
        ruleNameRepository.save(ruleName);

        //WHEN
        ResultActions response = mockMvc.perform(get("/ruleName/list")
                .contentType(MediaType.APPLICATION_JSON));

        // THEN
        response
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attribute("allRuleName", List.of(ruleName)));
    }

    @Test
    @WithMockUser(authorities = "Admin")
    void getAddRuleNameForm() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void validateAddRuleName() throws Exception {
        //WHEN
        ResultActions response = mockMvc.perform(post("/ruleName/validate")
                .with(csrf())
                .content(mapper.asJsonString(ruleName))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        //THEN
        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "Admin")
    void getUpdateRuleNameForm() throws Exception {
        RuleName ruleNameAdded = ruleNameRepository.save(ruleName);

        ResultActions response = mockMvc.perform(get("/ruleName/update/{id}", ruleNameAdded.getId())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void deleteRuleName() throws Exception {
        RuleName ruleNameAdded = ruleNameRepository.save(ruleName);

        ResultActions response = mockMvc.perform(get("/ruleName/delete/{id}", ruleNameAdded.getId())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isFound())
                .andExpect(redirectedUrl("/ruleName/list"));
    }
}
