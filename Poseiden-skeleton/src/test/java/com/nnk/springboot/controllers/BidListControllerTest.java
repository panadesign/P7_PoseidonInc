package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.CrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(BidListController.class)
class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CrudService<BidList> bidListServiceCrud;

    @MockBean
    private BidListRepository bidListRepository;

    @MockBean
    private UserRepository userRepository;
    
    
    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }
    
    @Test
    @WithMockUser(username = "admin", password = "AdminSpring_123")
    void getBidList() throws Exception {
        List<BidList> allBids = List.of(
                new BidList("Account", "Type", 12d)
        );

        when(bidListServiceCrud.getAll()).thenReturn(allBids);
        mockMvc.perform(get("/bidList/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"));
    }

    @Test
    @WithMockUser(username = "admin", password = "AdminSpring_123")
    void addBidForm() throws Exception {

        mockMvc.perform(get("/bidList/add")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser(username = "admin", password = "AdminSpring_123")
    void validate() throws Exception {
//		BidList bidList = new BidList(2, "Account1", "Type1", 12d);
//		when(bidListServiceCrud.add(bidList)).thenReturn(bidList);
//
//		mockMvc.perform(post("/bidList/validate")
//						.sessionAttr("bidList", bidList)
//						.param("account", bidList.getAccount())
//						.param("type", bidList.getType())
//						.param("bidQuantity", bidList.getBidQuantity().toString()))
//				.andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "admin", password = "AdminSpring_123")
    void showUpdateForm() throws Exception {
        BidList bidList = new BidList(1, "Account1", "Type1", 12d);
        when(bidListRepository.save(bidList)).thenReturn(bidList);
        when(bidListServiceCrud.getById(bidList.getId())).thenReturn(bidList);

        int id = bidList.getId();

        mockMvc.perform(get("/bidList/update/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void updateBid() {
    }

    @Test
    @WithMockUser(username = "admin", password = "AdminSpring_123")
    void deleteBid() throws Exception {
//        BidList bidList = new BidList(1, "Account1", "Type1", 12d);
//        when(bidListRepository.save(bidList)).thenReturn(bidList);
//        when(bidListServiceCrud.getById(bidList.getId())).thenReturn(bidList);
//        int id = bidList.getId();
//        mockMvc.perform(delete("/bidList/delete/{id}", id))
//                .andExpect(status().isNotFound())
//                .andExpect(view().name("/bidList/list"));

    }
}