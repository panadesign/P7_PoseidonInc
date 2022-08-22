package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CurvePoint")
public class CurvePoint {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	@NotNull(message = "Curve point id cannot be null")
	Integer curveId;
	Timestamp asOfDate;
	@NotNull(message = "Curve point term cannot be null")
	Double term;
	@NotNull(message = "Curve point value cannot be null")
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
