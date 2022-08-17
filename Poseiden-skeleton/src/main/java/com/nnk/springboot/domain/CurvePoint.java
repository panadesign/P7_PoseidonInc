package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

	public CurvePoint(Double term, Double value) {
		this.term = term;
		this.value = value;
	}

	public CurvePoint update(CurvePoint curvePoint) {
		this.term = curvePoint.getTerm();
		this.value = curvePoint.getValue();
		return this;
	}
}
