package com.example.demo.message.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ResultResponse {
    Object results;
    String message;
    HttpStatus status;
}
