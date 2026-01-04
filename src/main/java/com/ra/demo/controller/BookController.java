package com.ra.demo.controller;

import com.ra.demo.model.dto.ResponseWrapper;
import com.ra.demo.model.dto.request.BookDTO;
import com.ra.demo.model.dto.response.BookResponse;
import com.ra.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PreAuthorize("permitAll()")

    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "10") int size
//            @RequestParam(name = "sortBy",defaultValue = "book_id") String sortBy,
//            @RequestParam(name = "orderBy",defaultValue = "asc") String orderBy
    ) {
//        Sort sort = orderBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size);
        Page<BookResponse> bookResponsesDTO = bookService.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .message("Get book successfully")
                        .dataResponse(bookResponsesDTO)
                        .build());
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBookByTitleOrContent(
            @RequestParam(name = "keyword",required = true) String keyword,
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<BookResponse> bookResponsesDTO = bookService.searchBookByTitleOrContent(keyword, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .message("Get book successfully")
                        .dataResponse(bookResponsesDTO)
                        .build()
        );
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@ModelAttribute BookDTO bookDTO) {
        BookResponse bookResponse = bookService.save(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .message("Book successfully added")
                        .dataResponse(bookResponse)
                        .build()
        );
    }

}
