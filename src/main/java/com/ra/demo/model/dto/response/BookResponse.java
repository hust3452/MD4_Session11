package com.ra.demo.model.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookResponse {
    private Long book_id;
    private String title;
    private String author;
    private int quantity;
    private double price;
    private String img;
    private Boolean status;
}
