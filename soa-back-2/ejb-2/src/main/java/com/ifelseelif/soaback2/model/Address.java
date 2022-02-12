package com.ifelseelif.soaback2.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Address implements Serializable {
    private String zipCode;
    private Location town;
}
