package com.ifelseelif.soaback2.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Coordinates implements Serializable {
    private Float x;

    private float y; //Поле не может быть null
}