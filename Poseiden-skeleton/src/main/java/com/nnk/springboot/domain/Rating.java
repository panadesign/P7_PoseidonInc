package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Rating")
public class Rating {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	@NotBlank
	String moodysRating;
	@NotBlank
	String sandPRating;
	@NotBlank
	String fitchRating;
	@Min(1)
	Integer orderNumber;

	public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
		this.moodysRating = moodysRating;
		this.sandPRating = sandPRating;
		this.fitchRating = fitchRating;
		this.orderNumber = orderNumber;
	}

	public Rating update(Rating rating) {
		this.moodysRating = rating.getMoodysRating();
		this.sandPRating = rating.getSandPRating();
		this.fitchRating = rating.getFitchRating();
		this.orderNumber = rating.getOrderNumber();
		return this;
	}
}
