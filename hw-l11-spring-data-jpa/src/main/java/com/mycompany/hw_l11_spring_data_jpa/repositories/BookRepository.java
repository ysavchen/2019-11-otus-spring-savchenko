package com.mycompany.hw_l11_spring_data_jpa.repositories;

import com.mycompany.hw_l11_spring_data_jpa.domain.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph("book-entity-graph")
    List<Book> findByAuthorId(@Param("id") long id);

    @EntityGraph("book-entity-graph")
    List<Book> findByGenreId(@Param("id") long id);

    @Override
    @EntityGraph("book-entity-graph")
    List<Book> findAll();

    @Modifying
    @Query("update Book b set b.title = :title where b.id = :id")
    void updateTitle(@Param("id") long id, @Param("title") String title);
}
