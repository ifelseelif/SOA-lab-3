package com.ifelseelif.soaback2.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HttpException extends Exception {
    private String message;
    private int statusCode;
}