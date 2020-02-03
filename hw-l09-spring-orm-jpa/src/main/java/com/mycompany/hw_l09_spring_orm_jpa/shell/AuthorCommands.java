package com.mycompany.hw_l09_spring_orm_jpa.shell;

import com.mycompany.hw_l09_spring_orm_jpa.domain.Book;
import com.mycompany.hw_l09_spring_orm_jpa.service.AuthorDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import static java.util.stream.Collectors.joining;

@ShellComponent
@RequiredArgsConstructor
public class AuthorCommands {

    private final AuthorDbService dbService;

    @ShellMethod(value = "Find author by id", key = {"fai", "find-author-by-id"})
    public String findAuthorById(long id) {
        var optAuthor = dbService.getById(id);
        if (optAuthor.isEmpty()) {
            return "Author with id = " + id + " is not found";
        }

        var author = optAuthor.get();

        String books = "No books available";
        if (!author.getBooks().isEmpty()) {
            books = author.getBooks().stream()
                    .map(Book::title)
                    .collect(joining(", "));
        }

        return "Author: " + author.getName() + " " + author.getSurname() + "\n" +
                "Books: " + books;
    }
}
