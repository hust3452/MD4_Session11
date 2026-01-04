package com.ra.demo.model.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long book_id;

    @Column(unique=true)
    private String title;
    private String author;
    private int quantity;
    private double price;
    private String img;
    private Boolean status;
}
