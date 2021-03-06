package com.ifelseelif.soaback2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbDateFormat;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabWork implements Serializable {

    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    private String name; //Поле не может быть null, Строка не может быть пустой

    private Coordinates coordinates; //Поле не может быть null

    @JsonbDateFormat(value = "dd-MM-yyyy HH:mm")
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    private Integer minimalPoint; //Поле может быть null, Значение поля должно быть больше 0

    private Difficulty difficulty; //Поле может быть null

    private Discipline discipline; //Поле не может быть null

}