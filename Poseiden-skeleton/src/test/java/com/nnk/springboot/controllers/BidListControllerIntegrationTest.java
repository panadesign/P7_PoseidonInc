package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.BidListRepository;
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

import java.util.List;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
class BidListControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BidListRepository bidListRepository;

    @Autowired
    private Mapper mapper;

    private
    BidList bid;


    @BeforeEach
    public void init() {
        bid = new BidList("Account", "Type", 12d);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getBidListTest() throws Exception {
        //GIVEN
        bidListRepository.save(bid);

        //WHEN
        ResultActions response = mockMvc.perform(get("/bidList/list")
                .contentType(MediaType.APPLICATION_JSON));

        // THEN
        response
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attribute("allBidList", List.of(bid)));
    }

    @Test
    @WithMockUser(authorities = "Admin")
    void getAddBidListForm() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void validateAddNewBidList() throws Exception {
        //WHEN
        ResultActions response = mockMvc.perform(post("/bidList/validate")
                .with(csrf())
                .content(mapper.asJsonString(bid))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        //THEN
        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "Admin")
    void getUpdateBidListForm() throws Exception {
        BidList bidListAdded = bidListRepository.save(bid);

        ResultActions response = mockMvc.perform(get("/bidList/update/{id}", bidListAdded.getId())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "Admin")
    void getUpdateBidList() throws Exception {
//        BidList bidList = new BidList("Account", "Type", 5d);
//        BidList bidListAdded = bidListRepository.save(bidList);
//
//        ResultActions response = mockMvc.perform(post("/bidList/update/{id}", bidListAdded.getId())
//                .contentType(MediaType.APPLICATION_JSON));
//
//        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void deleteBid() throws Exception {
        BidList bidList = new BidList("Account", "Type", 5d);
        BidList bidListAdded = bidListRepository.save(bidList);

        ResultActions response = mockMvc.perform(get("/bidList/delete/{id}", bidListAdded.getId())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isFound())
                .andExpect(redirectedUrl("/bidList/list"));
    }
}
