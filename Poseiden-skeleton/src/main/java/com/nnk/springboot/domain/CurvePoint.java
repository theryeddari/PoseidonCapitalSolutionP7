package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Entity
@Table(name = "curvepoint")
@Getter
@Setter
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CurveId")
    private Byte curveId;

    @Column(name = "asOfDate")
    private Instant asOfDate;

    @Column(name = "term")
    @Positive(message = "{app.positive}")
    @NotNull(message = "{curvepoint.not-null}")
    private Double term;

    @Column(name = "value")
    @Positive(message = "{app.positive}")
    @NotNull(message = "{curvepoint.not-null}")
    private Double value;

    @Column(name = "creationDate")
    private Instant creationDate;
}
