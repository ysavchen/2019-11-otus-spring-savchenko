package com.mycompany.hw_l20_spring_webflux.dto;

import com.mycompany.hw_l20_spring_webflux.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AuthorDto {

    private String id;
    private String name;
    private String surname;

    public static Author toDomainObject(AuthorDto dto) {
        return new Author(dto.id, dto.name, dto.surname);
    }

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getName(), author.getSurname());
    }
}
