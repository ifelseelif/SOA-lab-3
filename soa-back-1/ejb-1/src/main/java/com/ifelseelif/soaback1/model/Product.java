package com.ifelseelif.soaback1.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    private String name; //Поле не может быть null, Строка не может быть пустой

    @Embedded
    private Coordinates coordinates; //Поле не может быть null

    @Column(updatable = false)
    @Temporal(TemporalType.DATE)
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    private Float price; //Поле может быть null, Значение поля должно быть больше 0

    private Long manufactureCost;

    @Enumerated(EnumType.STRING)
    private UnitOfMeasure unitOfMeasure; //Поле не может быть null

    @ManyToOne(fetch = FetchType.EAGER)
    private Organization manufacturer; //Поле может быть null
}