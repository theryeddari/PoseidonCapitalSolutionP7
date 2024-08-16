package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Entity class representing a Bid List in the application.
 */

@Getter
@Setter
@Entity
@Table(name = "bidlist")
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BidListId", unique = true, nullable = false)
    private Byte bidListId;

    @Column(name = "account", nullable = false, length = 30)
    @NotBlank(message = "{app.not-blank}")
    private String account;

    @Column(name = "type", nullable = false, length = 30)
    @NotBlank(message = "{app.not-blank}")
    private String type;

    @Column(name = "bidQuantity")
    @Positive(message = "{app.positive}")
    private Double bidQuantity;

    @Column(name = "askQuantity")
    private Double askQuantity;

    @Column(name = "bid")
    private Double bid;

    @Column(name = "ask")
    private Double ask;

    @Column(name = "benchmark", length = 125)
    private String benchmark;

    @Column(name = "bidListDate")
    private Instant bidListDate;

    @Column(name = "commentary", length = 125)
    private String commentary;

    @Column(name = "security", length = 125)
    private String security;

    @Column(name = "status", length = 10)
    private String status;

    @Column(name = "trader", length = 125)
    private String trader;

    @Column(name = "book", length = 125)
    private String book;

    @Column(name = "creationName", length = 125)
    private String creationName;

    @Column(name = "creationDate")
    private Instant creationDate;

    @Column(name = "revisionName", length = 125)
    private String revisionName;

    @Column(name = "revisionDate")
    private Instant revisionDate;

    @Column(name = "dealName", length = 125)
    private String dealName;

    @Column(name = "dealType", length = 125)
    private String dealType;

    @Column(name = "sourceListId", length = 125)
    private String sourceListId;

    @Column(name = "side", length = 125)
    private String side;

}
