package com.nnk.springboot.domain;

import com.nnk.springboot.service.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;

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
	@ValidPassword
	private String password;
	
	@NotBlank(message = "FullName is mandatory")
	private String fullname;
	
	@NotBlank(message = "Role is mandatory")
	private String role;
	
	public UserAccount(String username, String password, String fullname, String role) {
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.role = role;
	}
	
	public <E> UserAccount(String username, String password, ArrayList<E> role) {
		this.username = username;
		this.password = password;
		this.role = "role";
	}
	
	public UserAccount update(UserAccount userAccount) {
		this.username = userAccount.getUsername();
		this.password = userAccount.getPassword();
		this.fullname = userAccount.getFullname();
		this.role = userAccount.getRole();
		return this;
	}
	
}
