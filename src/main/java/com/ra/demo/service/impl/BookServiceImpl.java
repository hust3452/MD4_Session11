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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public BookResponse save(BookDTO bookDTO) {
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
    public Page<BookResponse> searchBookByTitleOrContent(String keyword, Pageable pageable) {
        Page<Book> books = bookRepository.findByTitleContainingIgnoreCase(keyword, pageable);
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
    public Book findById(long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<String> deleteById(long id) {
        Book book = findById(id);

        if (book != null) {
            try {
                bookRepository.delete(book);
                return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Delete failed", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Service not found", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Book updateBook(Long id, BookDTO bookDTO) {
        Book oldBook = findById(id);

        if (oldBook != null) {
            Book newBook = new Book();
            newBook.setTitle(bookDTO.getTitle());
            newBook.setAuthor(bookDTO.getAuthor());
            newBook.setQuantity(bookDTO.getQuantity());
            newBook.setPrice(bookDTO.getPrice());
            newBook.setStatus(bookDTO.getStatus());
            newBook.setBook_id(id);

            if (!bookDTO.getImg().isEmpty() || bookDTO.getImg().getSize() > 0) {
               newBook.setImg(uploadFileService.uploadFile(bookDTO.getImg()));
            } else {
                newBook.setImg(oldBook.getImg());
            }
            try {
                return bookRepository.save(newBook);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }
}
