package com.mycompany.hw_l17_spring_mvc_modern_apps.controllers;

import com.mycompany.hw_l17_spring_mvc_modern_apps.dto.AuthorDto;
import com.mycompany.hw_l17_spring_mvc_modern_apps.service.AuthorDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityNotFoundException;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private static final String VIEW_AUTHOR_FORM = "/html/authors/viewAuthor";

    private final AuthorDbService dbService;

    @GetMapping("/author/{id}")
    public String getAuthorById(@PathVariable("id") long id, Model model) {
        return dbService.getById(id).map(
                author -> {
                    model.addAttribute("author", AuthorDto.toDto(author));
                    return VIEW_AUTHOR_FORM;
                }).orElseThrow(() -> new EntityNotFoundException("Author with id = " + id + " is not found"));
    }
}
