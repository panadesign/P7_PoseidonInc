package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<UserAccount, Integer>, JpaSpecificationExecutor<UserAccount> {
    /**
     * Find by username optional.
     *
     * @param username the username
     * @return the optional
     */
    Optional<UserAccount> findByUsername(String username);
}
