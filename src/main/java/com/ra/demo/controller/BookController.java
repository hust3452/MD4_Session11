package com.ra.demo.controller;

import com.ra.demo.model.dto.ResponseWrapper;
import com.ra.demo.model.dto.request.BookDTO;
import com.ra.demo.model.dto.response.BookResponse;
import com.ra.demo.model.entity.Book;
import com.ra.demo.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PutMapping( "/edit/{id}")
    public ResponseEntity<?> editBook(@PathVariable long id,
                                      @Valid @ModelAttribute BookDTO bookDTO){
        Book book = bookService.updateBook(id,bookDTO);
        if(book != null){
            return new ResponseEntity<>(book, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Update faile",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable long id) {
        return new ResponseEntity<>(bookService.deleteById(id),HttpStatus.OK);
    }

}
