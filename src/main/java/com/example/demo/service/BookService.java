package com.example.demo.service;

import com.example.demo.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<Book> getBookDetail(Integer id);
    List<Book> getAllBooks();
    Book createBook(Book book);
    Book updateBook(Book book);
}