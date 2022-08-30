package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
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
@WebMvcTest(TradeController.class)
class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CrudService<Trade> tradeCrudService;

    @MockBean
    private TradeRepository tradeRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "admin", password = "AdminSpring_123")
    void getTrade() throws Exception {
        List<Trade> allTrades = List.of(
                new Trade("Account", "Type")
        );

        when(tradeCrudService.getAll()).thenReturn(allTrades);
        mockMvc.perform(get("/trade/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"));
    }

    @Test
    @WithMockUser(username = "admin", password = "AdminSpring_123")
    void showTradeForm() throws Exception {

        mockMvc.perform(get("/trade/add")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithMockUser(username = "admin", password = "AdminSpring_123")
    void validate() throws Exception {

    }

    @Test
    @WithMockUser(username = "admin", password = "AdminSpring_123")
    void showUpdateForm() throws Exception {
        Trade trade = new Trade(1,"Account1", "Type1");
        when(tradeRepository.save(trade)).thenReturn(trade);
        when(tradeCrudService.getById(trade.getId())).thenReturn(trade);

        int id = trade.getId();

        mockMvc.perform(get("/trade/update/{id}", id))
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