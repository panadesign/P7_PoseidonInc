package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
	
	@Pattern(regexp= "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()-[{}]:;',?/*~$^+=<>_])(?=\\S+$).{8,60}$", message = "Invalid password ! Password must contain at least 8 character, one or mmore uppercase, number and special character.")
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
