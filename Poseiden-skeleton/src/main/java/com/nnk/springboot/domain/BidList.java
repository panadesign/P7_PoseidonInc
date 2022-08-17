package com.nnk.springboot.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "Bidlist")
public class BidList {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer bidListId;
	@NotBlank
	String account;
	@NotBlank
	String type;
	@NotBlank
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

	public BidList(Integer bidListId, String account, String type, double bidQuantity) {
		this.bidListId = bidListId;
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
		this.askQuantity = bidList.getAskQuantity();
		this.bid = bidList.getBid();
		this.ask = bidList.getAsk();
		this.benchmark = bidList.getBenchmark();
		this.commentary = bidList.getCommentary();
		this.security = bidList.getSecurity();
		this.status = bidList.getStatus();
		this.trader = bidList.getTrader();
		this.book = bidList.getBook();
		this.creationName = bidList.getCreationName();
		this.revisionName = bidList.getRevisionName();
		this.revisionDate = bidList.getRevisionDate();
		this.dealName = bidList.getDealName();
		this.dealType = bidList.getDealType();
		this.side = bidList.getSide();
		return this;
	}
}
