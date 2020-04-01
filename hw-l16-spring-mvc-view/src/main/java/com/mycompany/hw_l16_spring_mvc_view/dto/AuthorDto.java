package com.mycompany.hw_l16_spring_mvc_view.dto;

import com.mycompany.hw_l16_spring_mvc_view.domain.Author;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthorDto {

    private final long id;
    private final String name;
    private final String surname;

    public static Author toDomainObject(AuthorDto dto) {
        return new Author(dto.id, dto.name, dto.surname);
    }

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getName(), author.getSurname());
    }
}