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

/**
 * The type Trade.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Trade")
public class Trade {
    /**
     * The Id.
     */
    @Id
	@GeneratedValue(strategy= GenerationType.AUTO)
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
     * The Buy quantity.
     */
    @NotNull(message = "Quantity cannot be null")
	@Min(1)
	Double buyQuantity;
    /**
     * The Sell quantity.
     */
    @Min(1)
	Double sellQuantity;
    /**
     * The Buy price.
     */
    Double buyPrice;
    /**
     * The Sell price.
     */
    Double sellPrice;
    /**
     * The Benchmark.
     */
    String benchmark;
    /**
     * The Trade date.
     */
    Timestamp tradeDate;
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
     * Instantiates a new Trade.
     *
     * @param id      the id
     * @param account the account
     * @param type    the type
     */
    public Trade(Integer id, String account, String type) {
		this.id = id;
		this.account = account;
		this.type = type;
	}

    /**
     * Instantiates a new Trade.
     *
     * @param account the account
     * @param type    the type
     */
    public Trade(String account, String type) {
		this.account = account;
		this.type = type;
	}

    /**
     * Instantiates a new Trade.
     *
     * @param account     the account
     * @param type        the type
     * @param buyQuantity the buy quantity
     */
    public Trade(String account, String type, Double buyQuantity) {
		this.account = account;
		this.type = type;
		this.buyQuantity = buyQuantity;
	}

    /**
     * Instantiates a new Trade.
     *
     * @param account      the account
     * @param type         the type
     * @param buyQuantity  the buy quantity
     * @param sellQuantity the sell quantity
     */
    public Trade(String account, String type, Double buyQuantity, Double sellQuantity) {
		this.account = account;
		this.type = type;
		this.buyQuantity = buyQuantity;
		this.sellQuantity = sellQuantity;
	}

    /**
     * Update trade.
     *
     * @param trade the trade
     * @return the trade
     */
    public Trade update(Trade trade) {
		this.account = trade.getAccount();
		this.type = trade.getType();
		this.buyQuantity = trade.getBuyQuantity();
		return this;
	}
}
