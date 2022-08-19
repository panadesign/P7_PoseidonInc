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
import java.sql.Timestamp;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CurvePoint")
public class CurvePoint {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	@NotBlank
	Integer curveId;
	Timestamp asOfDate;
	@NotBlank
	Double term;
	@NotBlank
	Double curveValue;
	Timestamp creationDate;

	public CurvePoint(Double term, Double curveValue) {
		this.term = term;
		this.curveValue = curveValue;
	}

	public CurvePoint update(CurvePoint curvePoint) {
		this.curveId = curvePoint.getCurveId();
		this.term = curvePoint.getTerm();
		this.curveValue = curvePoint.getCurveValue();
		return this;
	}
}
