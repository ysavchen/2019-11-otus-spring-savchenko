package com.mycompany.hw_l09_spring_orm_jpa.repositories;

import com.mycompany.hw_l09_spring_orm_jpa.domain.Genre;
import com.mycompany.hw_l09_spring_orm_jpa.repositories.GenreRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import(GenreRepositoryImpl.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class GenreRepositoryImplTests {

    private final Genre genre = new Genre(1, "Computers & Technology");
    private static final long NON_EXISTING_ID = 50;

    @Autowired
    private GenreRepositoryImpl genreRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void insertGenre() {
        var genre = new Genre("test");
        long id = genreRepository.insert(genre);
        assertEquals(2, id, "Invalid id for an inserted Genre");
    }

    @Test
    void getGenreById() {
        assertThat(genreRepository.getById(genre.id())).get()
                .hasFieldOrPropertyWithValue("id", genre.id())
                .hasFieldOrPropertyWithValue("name", genre.name());
    }

    @Test
    void getGenreByNonExistingId() {
        assertThat(genreRepository.getById(NON_EXISTING_ID)).isEmpty();
    }
}
