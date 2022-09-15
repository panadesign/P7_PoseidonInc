package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * The type Curve point.
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CurvePoint")
public class CurvePoint {
    /**
     * The Id.
     */
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    /**
     * The Curve id.
     */
    @NotNull(message = "Curve point id cannot be null")
	@Min(1)
    private Integer curveId;
    /**
     * The As of date.
     */
    private Timestamp asOfDate;
    /**
     * The Term.
     */
    @NotNull(message = "Curve point term cannot be null")
	@Min(1)
    private Double term;
    /**
     * The Curve value.
     */
    @NotNull(message = "Curve point value cannot be null")
	@Min(1)
    private Double curveValue;
    /**
     * The Creation date.
     */
    private Timestamp creationDate;

	
    /**
     * Instantiates a new Curve point.
     *
     * @param term       the term
     * @param curveValue the curve value
     */
    public CurvePoint(Double term, Double curveValue) {
		this.term = term;
		this.curveValue = curveValue;
	}

    /**
     * Instantiates a new Curve point.
     *
     * @param curveId    the curve id
     * @param term       the term
     * @param curveValue the curve value
     */
    public CurvePoint(Integer curveId, Double term, Double curveValue) {
		this.curveId = curveId;
		this.term = term;
		this.curveValue = curveValue;
	}


    /**
     * Update curve point.
     *
     * @param curvePoint the curve point
     * @return the curve point
     */
    public CurvePoint update(CurvePoint curvePoint) {
		this.curveId = curvePoint.getCurveId();
		this.term = curvePoint.getTerm();
		this.curveValue = curvePoint.getCurveValue();
		return this;
	}
}
