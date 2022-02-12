package com.ifelseelif.soaback1.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Location  implements Serializable {
    private double x;
    private int y;
    private Double z;
}
