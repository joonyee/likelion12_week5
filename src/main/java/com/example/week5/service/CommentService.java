package com.example.week5.service;

import com.example.week5.domain.Comment;
import com.example.week5.domain.Post;
import com.example.week5.dto.CommentDto;
import com.example.week5.repository.CommentRepository;
import com.example.week5.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }
    @Transactional
    public Comment registerComment(Comment comment) {
        return commentRepository.save(comment);
    }
    @Transactional
    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }
    @Transactional
    public Comment updateComment(Long id, CommentDto commentDto){
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            if (commentDto.getContent() != null) {
                commentDto.setContent(commentDto.getContent());
            }
            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Comment not found with id" + id);
        }
    }


    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }
    public List<Comment> getCommentByPostId(Long id){
        return commentRepository.findByPostId(id);
    }

}