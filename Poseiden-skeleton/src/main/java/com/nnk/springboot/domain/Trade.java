package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Trade")
public class Trade {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	Integer id;
	@NotBlank(message = "Account cannot be null")
	String account;
	@NotBlank(message = "Type cannot be null")
	String type;
	@NotNull(message = "Quantity cannot be null")
	@Min(1)
	Double buyQuantity;
	@Min(1)
	Double sellQuantity;
	Double buyPrice;
	Double sellPrice;
	String benchmark;
	Timestamp tradeDate;
	String security;
	String status;
	String trader;
	String book;
	String creationName;
	Timestamp creationDate;
	String revisionName;
	Timestamp revisionDate;
	String dealName;
	String dealType;
	String sourceListId;
	String side;

	public Trade(Integer id, String account, String type) {
		this.id = id;
		this.account = account;
		this.type = type;
	}

	public Trade(String account, String type) {
		this.account = account;
		this.type = type;
	}

	public Trade(String account, String type, Double buyQuantity) {
		this.account = account;
		this.type = type;
		this.buyQuantity = buyQuantity;
	}

	public Trade(String account, String type, Double buyQuantity, Double sellQuantity) {
		this.account = account;
		this.type = type;
		this.buyQuantity = buyQuantity;
		this.sellQuantity = sellQuantity;
	}
	
	public Trade update(Trade trade) {
		this.account = trade.getAccount();
		this.type = trade.getType();
		this.buyQuantity = trade.getBuyQuantity();
		return this;
	}
}
