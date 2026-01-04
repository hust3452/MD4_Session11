package com.ra.demo.service.impl;

import com.ra.demo.model.dto.request.BookDTO;
import com.ra.demo.model.dto.response.BookResponse;
import com.ra.demo.model.entity.Book;
import com.ra.demo.repository.BookRepository;
import com.ra.demo.service.BookService;
import com.ra.demo.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UploadFileService uploadFileService;

    @Override
    public Page<BookResponse> findAll(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);
        return books.map(book ->
                BookResponse.builder()
                        .book_id(book.getBook_id())
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .quantity(book.getQuantity())
                        .price(book.getPrice())
                        .img(book.getImg())
                        .status(book.getStatus())
                        .build());
    }

    @Override
    public BookResponse save(BookDTO bookDTO){
        String fileName = uploadFileService.uploadFile(bookDTO.getImg());

        Book book = Book.builder()
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .quantity(bookDTO.getQuantity())
                .price(bookDTO.getPrice())
                .img(fileName)
                .status(bookDTO.getStatus())
                .build();

        Book bookNew = bookRepository.save(book);
        return BookResponse.builder()
                .book_id(bookNew.getBook_id())
                .title(bookNew.getTitle())
                .author(bookNew.getAuthor())
                .quantity(bookNew.getQuantity())
                .price(bookNew.getPrice())
                .img(bookNew.getImg())
                .build();
    }

    @Override
    public Page<BookResponse> searchBookByTitleOrContent(String keyword,Pageable pageable){
        Page<Book> books = bookRepository.findByTitleContainingIgnoreCase(keyword,pageable);
        return books.map(book ->
                BookResponse.builder()
                        .book_id(book.getBook_id())
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .quantity(book.getQuantity())
                        .price(book.getPrice())
                        .img(book.getImg())
                        .status(book.getStatus())
                        .build());
    }

}
