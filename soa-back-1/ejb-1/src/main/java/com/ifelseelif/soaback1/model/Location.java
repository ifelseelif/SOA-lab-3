package com.ifelseelif.soaback1.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class Location  implements Serializable {
    private double x;
    private int y;
    private Double z;
}
