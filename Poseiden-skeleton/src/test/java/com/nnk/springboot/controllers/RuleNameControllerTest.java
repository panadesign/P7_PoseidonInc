package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.CrudService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(RuleNameController.class)
class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CrudService<RuleName> ruleNameCrudService;

    @MockBean
    private RuleNameRepository ruleNameRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "admin", password = "AdminSpring_123")
    void getRuleName() throws Exception {
        List<RuleName> allRuleNames = List.of(
                new RuleName("name", "description", "sdq", "ds", "dsf", "dsf")
        );

        when(ruleNameCrudService.getAll()).thenReturn(allRuleNames);
        mockMvc.perform(get("/ruleName/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"));
    }

    @Test
    @WithMockUser(username = "admin", password = "AdminSpring_123")
    void showRuleNameAddForm() throws Exception {

        mockMvc.perform(get("/ruleName/add")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithMockUser(username = "admin", password = "AdminSpring_123")
    void validate() throws Exception {

    }

    @Test
    @WithMockUser(username = "admin", password = "AdminSpring_123")
    void showUpdateForm() throws Exception {
        RuleName ruleName = new RuleName(12, "name", "description", "sdq", "ds", "dsf", "dsf");
        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);
        when(ruleNameCrudService.getById(ruleName.getId())).thenReturn(ruleName);

        int id = ruleName.getId();

        mockMvc.perform(get("/ruleName/update/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void updateBid() {
    }

    @Test
    @WithMockUser(username = "admin", password = "AdminSpring_123")
    void deleteBid() throws Exception {
    }
}