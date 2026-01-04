package com.ra.demo.service;

import com.ra.demo.model.dto.request.BookDTO;
import com.ra.demo.model.dto.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<BookResponse> findAll(Pageable pageable);


    BookResponse save(BookDTO bookDTO);

    Page<BookResponse> searchBookByTitleOrContent(String keyword, Pageable pageable);
}
