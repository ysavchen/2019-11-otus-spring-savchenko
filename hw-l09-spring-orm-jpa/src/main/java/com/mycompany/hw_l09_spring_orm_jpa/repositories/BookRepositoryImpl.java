package com.mycompany.hw_l09_spring_orm_jpa.repositories;

import com.mycompany.hw_l09_spring_orm_jpa.domain.Book;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long insert(@NonNull Book book) {
        if (book.id() <= 0) {
            em.persist(book);
        } else {
            em.merge(book);
        }
        return book.id();
    }

    @Override
    public Optional<Book> getById(long id) {
        try {
            var book = em.find(Book.class, id);
            return Optional.ofNullable(book);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getBooksByAuthorId(long id) {
        return em.createQuery(
                "select b " +
                        "from Book b " +
                        "join fetch b.author a " +
                        "join fetch b.genre g " +
                        "where a.id = :id", Book.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<Book> getAllBooks() {
        return em.createQuery(
                "select b from Book b " +
                        "join fetch b.author " +
                        "join fetch b.genre", Book.class)
                .getResultList();
    }

    @Override
    public boolean update(@NonNull Book book) {
        int rows = em.createQuery("update Book b set b.title = :title where b.id = :id")
                .setParameter("title", book.title())
                .setParameter("id", book.id())
                .executeUpdate();
        return rows > 0;
    }

    @Override
    public boolean deleteById(long id) {
        int rows = em.createQuery("delete from Book b where b.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        return rows > 0;
    }
}
