package com.mycompany.hw_l09_spring_orm_jpa.repositories;

import com.mycompany.hw_l09_spring_orm_jpa.domain.Author;

import java.util.Optional;

public interface AuthorRepository {

    long insert(Author author);

    Optional<Author> getById(long id);
}
