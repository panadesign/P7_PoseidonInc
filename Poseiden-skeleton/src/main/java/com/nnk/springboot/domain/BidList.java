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

/**
 * The type Bid list.
 */
@Data
@Entity
@RequiredArgsConstructor
@Table(name = "Bidlist")
public class BidList {
    /**
     * The Id.
     */
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
    /**
     * The Account.
     */
    @NotBlank(message = "Account cannot be null")
	String account;
    /**
     * The Type.
     */
    @NotBlank(message = "Type cannot be null")
	String type;
    /**
     * The Bid quantity.
     */
    @NotNull(message = "Bid quantity cannot be null")
	@Min(1)
	Double bidQuantity;
    /**
     * The Ask quantity.
     */
    Double askQuantity;
    /**
     * The Bid.
     */
    Double bid;
    /**
     * The Ask.
     */
    Double ask;
    /**
     * The Benchmark.
     */
    String benchmark;
    /**
     * The Bid list date.
     */
    Timestamp bidListDate;
    /**
     * The Commentary.
     */
    String commentary;
    /**
     * The Security.
     */
    String security;
    /**
     * The Status.
     */
    String status;
    /**
     * The Trader.
     */
    String trader;
    /**
     * The Book.
     */
    String book;
    /**
     * The Creation name.
     */
    String creationName;
    /**
     * The Creation date.
     */
    Timestamp creationDate;
    /**
     * The Revision name.
     */
    String revisionName;
    /**
     * The Revision date.
     */
    Timestamp revisionDate;
    /**
     * The Deal name.
     */
    String dealName;
    /**
     * The Deal type.
     */
    String dealType;
    /**
     * The Source list id.
     */
    String sourceListId;
    /**
     * The Side.
     */
    String side;

    /**
     * Instantiates a new Bid list.
     *
     * @param id          the id
     * @param account     the account
     * @param type        the type
     * @param bidQuantity the bid quantity
     */
    public BidList(Integer id, String account, String type, double bidQuantity) {
		this.id = id;
		this.account = account;
		this.type = type;
		this.bidQuantity = bidQuantity;
	}

    /**
     * Instantiates a new Bid list.
     *
     * @param account     the account
     * @param type        the type
     * @param bidQuantity the bid quantity
     */
    public BidList(String account, String type, double bidQuantity) {
		this.account = account;
		this.type = type;
		this.bidQuantity = bidQuantity;
	}

    /**
     * Update bid list.
     *
     * @param bidList the bid list
     * @return the bid list
     */
    public BidList update(BidList bidList) {
		this.account = bidList.getAccount();
		this.type = bidList.getType();
		this.bidQuantity = bidList.getBidQuantity();
		return this;
	}
}
