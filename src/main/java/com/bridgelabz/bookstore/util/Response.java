package com.bridgelabz.bookstore.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private String message;
    private int errorCode;
    private Object token;

}
