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

import java.net.URI;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * The type Trade controller integration test.
 */
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


    /**
     * Init.
     */
    @BeforeEach
    public void init() {
        trade = new Trade("Account", "Type", 1d);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    /**
     * Gets trade test.
     *
     * @throws Exception the exception
     */
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

    /**
     * Gets add trade form.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(authorities = "Admin")
    void getAddTradeForm() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk());
    }

    /**
     * Validate add trade.
     *
     * @throws Exception the exception
     */
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

    /**
     * Validate add trade error.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(authorities = "ADMIN")
    void validateAddTradeError() throws Exception {

        //WHEN
        ResultActions response = mockMvc.perform(post("/trade/validate")
                .with(csrf())
                .param("account", trade.getAccount())
                .param("type", "")
                .param("buyQuantity", String.valueOf(trade.getBuyQuantity()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        //THEN
        response.andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }


    /**
     * Gets update trade form.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(authorities = "Admin")
    void getUpdateTradeForm() throws Exception {
        Trade tradeAdded = tradeRepository.save(trade);

        ResultActions response = mockMvc.perform(get("/trade/update/{id}", tradeAdded.getId())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }

    /**
     * Update trade.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(authorities = "ADMIN")
    void updateTrade() throws Exception {
        Trade tradeAdded = tradeRepository.save(trade);

        tradeAdded.setAccount("accountUpdated");

        ResultActions response = mockMvc.perform(post("/trade/update/{id}", tradeAdded.getId())
                .with(csrf())
                .param("account", trade.getAccount())
                .param("type", trade.getType())
                .param("buyQuantity", String.valueOf(trade.getBuyQuantity()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/trade/list"));
    }

    /**
     * Update trade error.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(authorities = "ADMIN")
    void updateTradeError() throws Exception {
        Trade tradeAdded = tradeRepository.save(trade);

        tradeAdded.setAccount("accountUpdated");

        ResultActions response = mockMvc.perform(post("/trade/update/{id}", tradeAdded.getId())
                .with(csrf())
                .param("account", trade.getAccount())
                .param("type", "")
                .param("buyQuantity", String.valueOf(trade.getBuyQuantity()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }

    /**
     * Delete trade.
     *
     * @throws Exception the exception
     */
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
