package com.mycompany.hw_l17_spring_mvc_modern_apps.controllers;

import com.google.gson.Gson;
import com.mycompany.hw_l17_spring_mvc_modern_apps.domain.Author;
import com.mycompany.hw_l17_spring_mvc_modern_apps.domain.Book;
import com.mycompany.hw_l17_spring_mvc_modern_apps.domain.Genre;
import com.mycompany.hw_l17_spring_mvc_modern_apps.dto.BookDto;
import com.mycompany.hw_l17_spring_mvc_modern_apps.service.BookDbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookRestController.class)
public class BookRestControllerTests {

    private final Genre genre = new Genre(1, "Computers & Technology");
    private final Author prattAuthor = new Author(1, "Philip", "Pratt");
    private final Author learnAuthor = new Author(2, "Michael", "Learn");
    private final Book guideBook = new Book(1, "A Guide to SQL", prattAuthor, genre);
    private final Book conceptsBook = new Book(2, "Concepts of Database Management", prattAuthor, genre);
    private final Book sqlCodingBook = new Book(3, "SQL Programming and Coding", learnAuthor, genre);

    private final BookDto guideBookDto = BookDto.toDto(guideBook);
    private final BookDto conceptsBookDto = BookDto.toDto(conceptsBook);
    private final BookDto sqlCodingBookDto = BookDto.toDto(sqlCodingBook);

    private final List<Book> books = List.of(guideBook, conceptsBook, sqlCodingBook);
    private final List<BookDto> bookDtos = List.of(guideBookDto, conceptsBookDto, sqlCodingBookDto);

    private final Gson gson = new Gson();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookDbService dbService;

    @Test
    public void getAllBooks() throws Exception {
        when(dbService.getAllBooks()).thenReturn(books);
        mockMvc.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(bookDtos)));
    }

    @Test
    public void updateTitle() throws Exception {
        when(dbService.getById(guideBook.getId())).thenReturn(Optional.of(guideBook));

        var title = "test title";
        mockMvc.perform(
                patch("/api/book/{id}", guideBook.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(guideBookDto.setTitle(title))))
                .andExpect(status().isOk());
        verify(dbService, atLeastOnce()).updateTitle(guideBook.getId(), title);
    }

    @Test
    public void deleteBook() throws Exception {
        long id = 5;
        when(dbService.getAllBooks()).thenReturn(books);
        mockMvc.perform(delete("/api/book/{id}", id))
                .andExpect(status().isOk());
        verify(dbService, times(1)).deleteById(id);
    }

    @Test
    public void addBook() throws Exception {
        when(dbService.save(any())).thenReturn(guideBook);
        mockMvc.perform(
                post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(guideBookDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(guideBookDto)));
    }
}
