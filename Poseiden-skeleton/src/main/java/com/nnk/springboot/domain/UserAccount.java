package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * The type User account.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "UserAccount")
public class UserAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Integer id;
	
	@NotBlank(message = "Username is mandatory")
	private String username;

	@NotBlank(message = "Password is mandatory")
	private String password;
	
	@NotBlank(message = "FullName is mandatory")
	private String fullname;
	
	@NotBlank(message = "Role is mandatory")
	private String role;

    /**
     * Instantiates a new User account.
     *
     * @param username the username
     * @param password the password
     * @param fullname the fullname
     * @param role     the role
     */
    public UserAccount(String username, String password, String fullname, String role) {
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.role = role;
	}

    /**
     * Update user account.
     *
     * @param userAccount the user account
     * @return the user account
     */
    public UserAccount update(UserAccount userAccount) {
		this.username = userAccount.getUsername();
		this.password = userAccount.getPassword();
		this.fullname = userAccount.getFullname();
		this.role = userAccount.getRole();
		return this;
	}

}
