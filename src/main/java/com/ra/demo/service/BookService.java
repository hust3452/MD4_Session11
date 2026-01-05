package com.ra.demo.service;

import com.ra.demo.model.dto.request.BookDTO;
import com.ra.demo.model.dto.response.BookResponse;
import com.ra.demo.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
    Page<BookResponse> findAll(Pageable pageable);


    BookResponse save(BookDTO bookDTO);

    Page<BookResponse> searchBookByTitleOrContent(String keyword, Pageable pageable);

    Book findById(long id);

    ResponseEntity<String> deleteById(long id);

    Book updateBook(Long id, BookDTO bookDTO);
}
