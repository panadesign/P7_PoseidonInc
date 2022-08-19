package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User {
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

	public User(String username, String password, String fullname, String role) {
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.role = role;
	}

	public User update(User user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.fullname = user.getFullname();
		this.role = user.getRole();
		return this;
	}

}
