package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Rulename")
public class RuleName {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	@NotBlank
	String name;
	@NotBlank
	String description;
	@NotBlank
	String json;
	@NotBlank
	String template;
	@NotBlank
	String sqlStr;
	@NotBlank
	String sqlPart;

	public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
		this.name = name;
		this.description = description;
		this.json = json;
		this.template = template;
		this.sqlStr = sqlStr;
		this.sqlPart = sqlPart;
	}

	public RuleName update(RuleName ruleName) {
		this.name = ruleName.getName();
		this.description = ruleName.getDescription();
		this.json = ruleName.getJson();
		this.template = ruleName.getTemplate();
		this.sqlStr = ruleName.getSqlStr();
		this.sqlPart = ruleName.getSqlPart();
		return this;
	}
}
