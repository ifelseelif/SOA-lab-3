package com.ifelseelif.soaback1.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class Address implements Serializable {
    private String zipCode;

    @Embedded
    private Location town;
}
