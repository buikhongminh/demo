package com.example.demo.service;

import com.example.demo.entity.Author;

public interface AuthorService {
    Author createAuthor (Author author);
    Author getAuthorByName(String name);
}