package com.mycompany.hw_l07_dao_spring_jdbc.shell;

import com.mycompany.hw_l07_dao_spring_jdbc.domain.Author;
import com.mycompany.hw_l07_dao_spring_jdbc.domain.Book;
import com.mycompany.hw_l07_dao_spring_jdbc.domain.Genre;
import com.mycompany.hw_l07_dao_spring_jdbc.service.BookDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.function.Function;

import static java.util.stream.Collectors.joining;
import static org.springframework.shell.standard.ShellOption.NULL;

@ShellComponent
@RequiredArgsConstructor
public class BookCommands {

    private final BookDbService dbService;

    @ShellMethod(value = "Add book", key = {"ab", "add-book"})
    public String addBook(String title,
                          @ShellOption(defaultValue = NULL) String authorName,
                          @ShellOption(defaultValue = NULL) String authorSurname,
                          @ShellOption(defaultValue = NULL) String genre) {

        var book = new Book(title);
        if (authorName != null && authorSurname != null) {
            book.author(new Author(authorSurname, authorSurname));
        }
        if (genre != null) {
            book.genre(new Genre(genre));
        }

        return "Added book with id = " + dbService.insert(book);
    }

    @ShellMethod(value = "Find book by id", key = {"fbi", "find-book-by-id"})
    public String findBookById(long id) {
        var optBook = dbService.getById(id);
        if (optBook.isEmpty()) {
            return "Book with id = " + id + " is not found";
        }

        var book = optBook.get();
        String author = "not defined";
        String genre = "not defined";

        if (book.author() != null) {
            author = book.author().getName() + " " + book.author().getSurname();
        }
        if (book.genre() != null) {
            genre = book.genre().getName();
        }

        return "Title: " + book.title() + "\n" +
                "Author: " + author + "\n" +
                "Genre: " + genre;
    }

    @ShellMethod(value = "Find books by author id", key = {"fbai", "find-books-by-author-id"})
    public String findBooksByAuthorId(long id) {
        var books = dbService.getBooksByAuthorId(id);
        if (books.isEmpty()) {
            return "No books with authorId = " + id + " found";
        }

        Function<Book, String> bookFunc = book -> {
            String genre = "not defined";

            if (book.genre() != null) {
                genre = book.genre().getName();
            }
            return book.title() + ", " + genre;
        };
        var booksStr = books.stream()
                .map(bookFunc)
                .collect(joining("\n"));

        var author = books.get(0).author();
        return "Books with author (id = " + author.getId() + ", " +
                author.getName() + " " + author.getSurname() + "):" +
                "\n" + booksStr;
    }

    @ShellMethod(value = "Update title for a book", key = {"utb", "update-title-for-book"})
    public String updateTitle(long id, String newTitle) {
        var book = new Book(id, newTitle);
        boolean isUpdated = dbService.update(book);
        if (isUpdated) {
            return "Title is changed for a book with id = " + id;
        }
        return "Book with id = " + id + " is not found";
    }

    @ShellMethod(value = "Delete book by id", key = {"dbi", "delete-book-by-id"})
    public String deleteBookById(long id) {
        boolean isDeleted = dbService.deleteById(id);
        if (isDeleted) {
            return "Deleted book with id = " + id;
        }
        return "Book with id = " + id + " is not found";
    }
}
