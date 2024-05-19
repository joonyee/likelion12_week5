package com.example.week5.service;

import com.example.week5.domain.Comment;
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
    public Comment registerPost(Comment comment) {
        return commentRepository.save(comment);
    }
    public List<Comment> getAllPosts() {
        return commentRepository.findAll();
    }
    public Optional<Comment> getPostById(Long id) {
        return commentRepository.findById(id);
    }

}