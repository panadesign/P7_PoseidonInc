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

/**
 * The type Rule name.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Rulename")
public class RuleName {
    /**
     * The Id.
     */
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
    /**
     * The Name.
     */
    @NotBlank(message = "Name cannot be null")
	private String name;
    /**
     * The Description.
     */
    @NotBlank(message = "Description cannot be null")
	private String description;
    /**
     * The Json.
     */
    @NotBlank(message = "Json cannot be null")
	private String json;
    /**
     * The Template.
     */
    @NotBlank(message = "Template cannot be null")
	private String template;
    /**
     * The Sql str.
     */
    @NotBlank(message = "Sql str cannot be null")
	private String sqlStr;
    /**
     * The Sql part.
     */
    @NotBlank(message = "Sql part cannot be null")
	private String sqlPart;

    /**
     * Instantiates a new Rule name.
     *
     * @param name        the name
     * @param description the description
     * @param json        the json
     * @param template    the template
     * @param sqlStr      the sql str
     * @param sqlPart     the sql part
     */
    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
		this.name = name;
		this.description = description;
		this.json = json;
		this.template = template;
		this.sqlStr = sqlStr;
		this.sqlPart = sqlPart;
	}

    /**
     * Update rule name.
     *
     * @param ruleName the rule name
     * @return the rule name
     */
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
