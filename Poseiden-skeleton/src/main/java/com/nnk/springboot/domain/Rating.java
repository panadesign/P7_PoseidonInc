package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * The type Rating.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Rating")
public class Rating {
    /**
     * The Id.
     */
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
    /**
     * The Moodys rating.
     */
    @NotBlank(message = "Moody's rating cannot be null")
	private String moodysRating;
    /**
     * The Sand p rating.
     */
    @NotBlank(message = "Sand rating cannot be null")
	private String sandPRating;
    /**
     * The Fitch rating.
     */
    @NotBlank(message = "Fitch rating cannot be null")
	private String fitchRating;
    /**
     * The Order number.
     */
    @NotNull(message = "Order number cannot be null")
	@Min(1)
	private Integer orderNumber;

    /**
     * Instantiates a new Rating.
     *
     * @param moodysRating the moodys rating
     * @param sandPRating  the sand p rating
     * @param fitchRating  the fitch rating
     * @param orderNumber  the order number
     */
    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
		this.moodysRating = moodysRating;
		this.sandPRating = sandPRating;
		this.fitchRating = fitchRating;
		this.orderNumber = orderNumber;
	}

    /**
     * Update rating.
     *
     * @param rating the rating
     * @return the rating
     */
    public Rating update(Rating rating) {
		this.moodysRating = rating.getMoodysRating();
		this.sandPRating = rating.getSandPRating();
		this.fitchRating = rating.getFitchRating();
		this.orderNumber = rating.getOrderNumber();
		return this;
	}
}
