package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "rulename")
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", unique = true, nullable = false)
    private Byte id;

    @Size(max = 125)
    @Column(name = "name", length = 125)
    @NotBlank
    private String name;

    @Size(max = 125)
    @Column(name = "description", length = 125)
    private String description;

    @Size(max = 125)
    @Column(name = "json", length = 125)
    private String json;

    @Size(max = 512)
    @Column(name = "template", length = 512)
    private String template;

    @Size(max = 125)
    @Column(name = "sqlStr", length = 125)
    private String sqlStr;

    @Size(max = 125)
    @Column(name = "sqlPart", length = 125)
    private String sqlPart;

}
