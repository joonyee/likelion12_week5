package com.example.week5.service;

import com.example.week5.domain.Member;
import com.example.week5.domain.Post;
import com.example.week5.repository.MemberRepository;
import com.example.week5.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }
    @Transactional
    public Post registerPost(Post post) {
        return postRepository.save(post);
    }
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

}
