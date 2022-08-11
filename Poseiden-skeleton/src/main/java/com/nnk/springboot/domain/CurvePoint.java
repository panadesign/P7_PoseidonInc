package com.nnk.springboot.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "Curvepoint")
public class CurvePoint {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotBlank
	Integer id;
	@NotBlank
	Integer curveId;
	Timestamp asOfDate;
	@NotBlank
	Double term;
	@NotBlank
	Double value;
	Timestamp creationDate;
}
