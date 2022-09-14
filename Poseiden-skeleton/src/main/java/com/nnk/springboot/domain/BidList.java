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
    private Integer id;
    /**
     * The Account.
     */
    @NotBlank(message = "Account cannot be null")
    private String account;
    /**
     * The Type.
     */
    @NotBlank(message = "Type cannot be null")
    private String type;
    /**
     * The Bid quantity.
     */
    @NotNull(message = "Bid quantity cannot be null")
	@Min(1)
    private Double bidQuantity;
    /**
     * The Ask quantity.
     */
    private Double askQuantity;
    /**
     * The Bid.
     */
    private Double bid;
    /**
     * The Ask.
     */
    private Double ask;
    /**
     * The Benchmark.
     */
    private String benchmark;
    /**
     * The Bid list date.
     */
    private Timestamp bidListDate;
    /**
     * The Commentary.
     */
    private String commentary;
    /**
     * The Security.
     */
    private String security;
    /**
     * The Status.
     */
    private String status;
    /**
     * The Trader.
     */
    private String trader;
    /**
     * The Book.
     */
    private String book;
    /**
     * The Creation name.
     */
    private String creationName;
    /**
     * The Creation date.
     */
    private Timestamp creationDate;
    /**
     * The Revision name.
     */
    private String revisionName;
    /**
     * The Revision date.
     */
    private Timestamp revisionDate;
    /**
     * The Deal name.
     */
    private String dealName;
    /**
     * The Deal type.
     */
    private String dealType;
    /**
     * The Source list id.
     */
    private String sourceListId;
    /**
     * The Side.
     */
    private String side;

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
