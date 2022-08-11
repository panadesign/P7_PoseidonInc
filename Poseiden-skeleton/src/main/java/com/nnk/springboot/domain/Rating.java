package com.nnk.springboot.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "Rating")
public class Rating {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	String moodysRating;
	String sandPRating;
	String fitchRating;
	Integer orderNumber;
}
