package com.ra.demo.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookDTO {
    @NotNull(message = "title can not null")
    private String title;

    @NotNull(message = "author can not null")
    private String author;
    @NotNull(message = "quantity can not null")
    private int quantity;
    @NotNull(message = "price can not null")
    private double price;
    private MultipartFile img;
    private Boolean status = true;
}
