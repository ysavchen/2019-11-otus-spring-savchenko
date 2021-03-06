package com.mycompany.hw_l11_spring_data_jpa.service;

import com.mycompany.hw_l11_spring_data_jpa.domain.Book;
import com.mycompany.hw_l11_spring_data_jpa.domain.Comment;
import com.mycompany.hw_l11_spring_data_jpa.repositories.BookRepository;
import com.mycompany.hw_l11_spring_data_jpa.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentDbServiceImpl implements CommentDbService {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getCommentsByBookId(long id) {
        return commentRepository.findByBookId(id);
    }

    @Override
    public long addCommentByBookId(long id, Comment comment) {
        Optional<Book> optBook = bookRepository.findById(id);
        if (optBook.isPresent()) {
            comment.setBook(optBook.get());
            return commentRepository.save(comment).getId();
        }
        return 0;
    }

    @Override
    public void deleteCommentByBookId(long id, Comment comment) {
        commentRepository.deleteByBookId(id, comment);
    }
}
