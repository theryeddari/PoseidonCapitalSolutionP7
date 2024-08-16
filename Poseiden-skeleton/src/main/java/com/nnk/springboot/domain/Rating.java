package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "rating")
@Getter
@Setter
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", unique = true, nullable = false)
    private Byte id;


    @Size(max = 125)
    @Column(name = "moodysRating", length = 125)
    private String moodysRating;

    @Size(max = 125)
    @Column(name = "sandPRating", length = 125)
    private String sandPRating;

    @Size(max = 125)
    @Column(name = "fitchRating", length = 125)
    private String fitchRating;

    @Column(name = "orderNumber")
    @Positive(message = "{app.positive}")
    @NotNull(message = "{app.not-null}")
    private Byte orderNumber;

}
