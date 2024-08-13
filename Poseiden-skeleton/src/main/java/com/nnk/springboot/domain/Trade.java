package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "trade")
public class Trade {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TradeId", unique = true, nullable = false)
    private Byte tradeId;


    @Size(max = 30)
    @NotNull
    @Column(name = "account", nullable = false, length = 30)
    private String account;

    @Size(max = 30)
    @NotNull
    @Column(name = "type", nullable = false, length = 30)
    private String type;

    @Column(name = "buyQuantity")
    @NotNull(message = "{app.not-null}")
    @Positive(message = "{app.positive}")
    private Double buyQuantity;

    @Column(name = "sellQuantity")
    private Double sellQuantity;

    @Column(name = "buyPrice")
    private Double buyPrice;

    @Column(name = "sellPrice")
    private Double sellPrice;

    @Column(name = "tradeDate")
    private Instant tradeDate;

    @Size(max = 125)
    @Column(name = "security", length = 125)
    private String security;

    @Size(max = 10)
    @Column(name = "status", length = 10)
    private String status;

    @Size(max = 125)
    @Column(name = "trader", length = 125)
    private String trader;

    @Size(max = 125)
    @Column(name = "benchmark", length = 125)
    private String benchmark;

    @Size(max = 125)
    @Column(name = "book", length = 125)
    private String book;

    @Size(max = 125)
    @Column(name = "creationName", length = 125)
    private String creationName;

    @Column(name = "creationDate")
    private Instant creationDate;

    @Size(max = 125)
    @Column(name = "revisionName", length = 125)
    private String revisionName;

    @Column(name = "revisionDate")
    private Instant revisionDate;

    @Size(max = 125)
    @Column(name = "dealName", length = 125)
    private String dealName;

    @Size(max = 125)
    @Column(name = "dealType", length = 125)
    private String dealType;

    @Size(max = 125)
    @Column(name = "sourceListId", length = 125)
    private String sourceListId;

    @Size(max = 125)
    @Column(name = "side", length = 125)
    private String side;

}
