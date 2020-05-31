package com.mycompany.hw_l20_spring_webflux.service;

import com.mycompany.hw_l20_spring_webflux.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDbService {

    Book save(Book book);

    Optional<Book> getById(long id);

    List<Book> getAllBooks();

    void updateTitle(long id, String title);

    void deleteById(long id);
}
