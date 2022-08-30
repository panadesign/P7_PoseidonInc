package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.UserAccount;
import com.nnk.springboot.repositories.UserRepository;
import com.sun.xml.bind.v2.model.core.ID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
class UserAccountControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getUserListByAdmin() throws Exception {
        //GIVEN
        var newUser = new UserAccount("Account", "Testtest_2022", "name", "USER");
        var savedUser = userRepository.save(newUser);

        //WHEN
        var response = mockMvc.perform(get("/user/list")
                .contentType(MediaType.APPLICATION_JSON));

        //THEN

        response.andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attribute("users", List.of(savedUser)));
    }

    @Test
    @WithMockUser(authorities = "USER")
    void getUserListByUser() throws Exception {
        //GIVEN
        UserAccount newUser = new UserAccount("Account", "Testtest_2022", "name", "USER");
        userRepository.save(newUser);

        //WHEN
        ResultActions response = mockMvc.perform(get("/user/list")
                .contentType(MediaType.APPLICATION_JSON));

        //THEN

        response.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getAddUserForm() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void validateAddNewUserAccount() throws Exception {
        UserAccount userAccountToAdd = new UserAccount("userName", "Password_2022", "fullName", "USER");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userAccountToAdd.setPassword(encoder.encode(userAccountToAdd.getPassword()));
        UserAccount userAccountAdded = userRepository.save(userAccountToAdd);

        ResultActions response = mockMvc.perform(post("/user/validate")
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isCreated())
                .andExpect(model().attribute("users", List.of(userAccountAdded)))
                .andExpect(view().name("/user/list"));

    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getUpdateUserAccountForm() throws Exception {
        UserAccount userAccountToAdd = new UserAccount("userName", "Password_2022", "fullName", "USER");
        UserAccount userAccountAdded = userRepository.save(userAccountToAdd);

        ResultActions response = mockMvc.perform(get("/user/update/{id}", userAccountAdded.getId())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }
}
