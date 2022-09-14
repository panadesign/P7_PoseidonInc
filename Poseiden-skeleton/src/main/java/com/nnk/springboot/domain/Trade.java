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
     * The Buy quantity.
     */
    @NotNull(message = "Quantity cannot be null")
	@Min(1)
    private Double buyQuantity;
    /**
     * The Sell quantity.
     */
    @Min(1)
    private Double sellQuantity;
    /**
     * The Buy price.
     */
    private Double buyPrice;
    /**
     * The Sell price.
     */
    private Double sellPrice;
    /**
     * The Benchmark.
     */
    private String benchmark;
    /**
     * The Trade date.
     */
    private Timestamp tradeDate;
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
