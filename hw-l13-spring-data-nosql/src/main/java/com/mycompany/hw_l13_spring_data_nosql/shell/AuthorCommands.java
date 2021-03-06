package com.mycompany.hw_l13_spring_data_nosql.shell;

import com.mycompany.hw_l13_spring_data_nosql.domain.Author;
import com.mycompany.hw_l13_spring_data_nosql.service.AuthorDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class AuthorCommands {

    private final AuthorDbService dbService;

    @ShellMethod(value = "Add author", key = {"aa", "add-author"})
    public String addAuthor(String name, String surname) {
        String id = dbService.save(new Author(name, surname));
        return "Added author with id = " + id;
    }

    @ShellMethod(value = "Find author by id", key = {"fai", "find-author-by-id"})
    public String findAuthorById(String id) {
        var optAuthor = dbService.getById(id);
        if (optAuthor.isEmpty()) {
            return "Author with id = " + id + " is not found";
        }

        var author = optAuthor.get();
        return "Author: " + author.getName() + " " + author.getSurname();
    }
}
