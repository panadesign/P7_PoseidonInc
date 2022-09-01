package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.CrudService;
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

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@Transactional
class TradeControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private CrudService<Trade> crudService;

    private Trade trade;


    @BeforeEach
    public void init() {
        trade = new Trade("Account", "Type", 1d);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getTradeTest() throws Exception {
        //GIVEN
        tradeRepository.save(trade);

        //WHEN
        ResultActions response = mockMvc.perform(get("/trade/list")
                .contentType(MediaType.APPLICATION_JSON));

        // THEN
        response
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attribute("allTrades", List.of(trade)));
    }

    @Test
    @WithMockUser(authorities = "Admin")
    void getAddTradeForm() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void validateAddTrade() throws Exception {

        //WHEN
        ResultActions response = mockMvc.perform(post("/trade/validate")
                .with(csrf())
                        .param("account", trade.getAccount())
                        .param("type", trade.getType())
                        .param("buyQuantity", String.valueOf(trade.getBuyQuantity()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        //THEN
        response.andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/trade/list"));
    }

    @Test
    @WithMockUser(authorities = "Admin")
    void getUpdateTradeForm() throws Exception {
        Trade tradeAdded = tradeRepository.save(trade);

        ResultActions response = mockMvc.perform(get("/trade/update/{id}", tradeAdded.getId())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void updateTrade() throws Exception {
        Trade tradeAdded = tradeRepository.save(trade);

        tradeAdded.setAccount("accountUpdated");

        ResultActions response = mockMvc.perform(post("/trade/update/{id}",tradeAdded.getId())
                .with(csrf())
                .param("account", trade.getAccount())
                .param("type", trade.getType())
                .param("buyQuantity", String.valueOf(trade.getBuyQuantity()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/trade/list"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void deleteTrade() throws Exception {
        Trade tradeAdded = tradeRepository.save(trade);

        ResultActions response = mockMvc.perform(get("/trade/delete/{id}", tradeAdded.getId())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isFound())
                .andExpect(redirectedUrl("/trade/list"));
    }
}
