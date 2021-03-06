package com.mycompany.hw_l07_dao_spring_jdbc.service;

import com.mycompany.hw_l07_dao_spring_jdbc.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDbService {

    long insert(Book book);

    Optional<Book> getById(long id);

    List<Book> getBooksByAuthorId(long id);

    boolean update(Book book);

    boolean deleteById(long id);
}
