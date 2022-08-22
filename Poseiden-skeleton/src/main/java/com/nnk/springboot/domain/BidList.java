package com.nnk.springboot.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "Bidlist")
public class BidList {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	@NotBlank(message = "Account cannot be null")
	String account;
	@NotBlank(message = "Type cannot be null")
	String type;
	@NotNull(message = "Bid quantity cannot be null")
	@Min(1)
	Double bidQuantity;
	Double askQuantity;
	Double bid;
	Double ask;
	String benchmark;
	Timestamp bidListDate;
	String commentary;
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

	public BidList(Integer id, String account, String type, double bidQuantity) {
		this.id = id;
		this.account = account;
		this.type = type;
		this.bidQuantity = bidQuantity;
	}

	public BidList(String account, String type, double bidQuantity) {
		this.account = account;
		this.type = type;
		this.bidQuantity = bidQuantity;
	}

	public BidList update(BidList bidList) {
		this.account = bidList.getAccount();
		this.type = bidList.getType();
		this.bidQuantity = bidList.getBidQuantity();
		return this;
	}
}
