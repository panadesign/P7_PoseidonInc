package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.UserAccount;
import com.nnk.springboot.exception.InvalidPasswordException;
import com.nnk.springboot.exception.UserAlreadyExistException;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.CrudService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.regex.Pattern;

/**
 * The type User controller.
 */
@Controller
@Log4j2
public class UserController {
    @Qualifier("UserAccount")
    private final CrudService<UserAccount> crudService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Instantiates a new User controller.
     *
     * @param crudService the crud service
     */
    UserController(CrudService<UserAccount> crudService) {
        this.crudService = crudService;
    }

    /**
     * Home string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userRepository.findAll());
        log.debug("Get user account list");
        return "user/list";
    }

    /**
     * Add user string.
     *
     * @param userAccount the user account
     * @return the string
     */
    @GetMapping("/user/add")
    public String addUser(UserAccount userAccount) {
        log.debug("Get add user account form");
        return "user/add";
    }

    /**
     * Validate string.
     *
     * @param userAccount the user account
     * @param result      the result
     * @param model       the model
     * @return the string
     */
    @PostMapping("/user/validate")
    public String validate(@Valid UserAccount userAccount, BindingResult result, Model model) {
        log.debug("Add a new user account");

        if (result.hasErrors()) {
            return "user/add";
        }

        try {
            crudService.add(userAccount);
            model.addAttribute("users", userRepository.findAll());
        } catch(Exception e) {
            result.addError(new ObjectError("userAccount", e.getMessage()));
            return "user/add";
        }

        log.debug("A new user Account has been created and user/validate redirect to user/list");
        return "redirect:/user/list";
    }

    /**
     * Show update form string.
     *
     * @param id          the id
     * @param userAccount the user account
     * @param model       the model
     * @return the string
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, UserAccount userAccount, Model model) {
        log.debug("Get update form for id " + id);
        userAccount = crudService.getById(id);
        model.addAttribute("user", userAccount);
        return "user/update";
    }

    /**
     * Update user string.
     *
     * @param id          the id
     * @param userAccount the user account
     * @param result      the result
     * @param model       the model
     * @return the string
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid UserAccount userAccount, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }
        crudService.update(id, userAccount);
        model.addAttribute("users", userRepository.findAll());
        log.debug("User account with id " + userAccount.getId() + " has been updated and user/update/" + userAccount.getId() + " is redirected to user/list");
        return "redirect:/user/list";
    }

    /**
     * Delete user string.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        UserAccount userAccount = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(userAccount);
        model.addAttribute("users", userRepository.findAll());
        log.debug("User account with id " + userAccount.getId() + " has been deleted and user/delete/" + id + " is redirected to user/list");
        return "redirect:/user/list";
    }
}
