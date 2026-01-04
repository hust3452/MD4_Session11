package com.ra.demo.model.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponseWrapper <T> {
    private String message;
    private int code;
    private T dataResponse;
}
