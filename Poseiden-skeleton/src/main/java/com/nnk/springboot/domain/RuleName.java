package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Rulename")
public class RuleName {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	Integer id;
	String name;
	String description;
	String json;
	String template;
	String sqlStr;
	String sqlPart;
}
