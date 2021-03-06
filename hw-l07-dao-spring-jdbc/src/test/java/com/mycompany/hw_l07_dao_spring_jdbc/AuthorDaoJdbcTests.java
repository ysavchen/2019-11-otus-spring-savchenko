package com.mycompany.hw_l07_dao_spring_jdbc;

import com.mycompany.hw_l07_dao_spring_jdbc.dao.AuthorDaoJdbc;
import com.mycompany.hw_l07_dao_spring_jdbc.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@Import(AuthorDaoJdbc.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthorDaoJdbcTests {

    private final Author author = new Author(1, "Philip", "Pratt");
    private static final long NON_EXISTING_ID = 50;

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    void insertAuthor() {
        var author = new Author("Michael", "Smith");
        long id = authorDaoJdbc.insert(author);
        assertEquals(2, id, "Invalid id for an inserted Author");
        assertThat(authorDaoJdbc.getById(id)).get()
                .hasFieldOrPropertyWithValue("name", author.getName())
                .hasFieldOrPropertyWithValue("surname", author.getSurname());
    }

    @Test
    void insertAuthorWithId() {
        var author = new Author(100, "Michael", "Smith");
        assertEquals(2, authorDaoJdbc.insert(author));
    }

    @Test
    void getAuthorById() {
        assertThat(authorDaoJdbc.getById(author.getId())).get()
                .hasFieldOrPropertyWithValue("id", author.getId())
                .hasFieldOrPropertyWithValue("name", author.getName())
                .hasFieldOrPropertyWithValue("surname", author.getSurname());
    }

    @Test
    void getAuthorByNonExistingId() {
        assertThat(authorDaoJdbc.getById(NON_EXISTING_ID)).isEmpty();
    }
}
