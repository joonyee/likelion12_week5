package com.example.week5.Controller;

import com.example.week5.domain.Member;
import com.example.week5.domain.Post;
import com.example.week5.dto.MemberDto;
import com.example.week5.dto.PostDto;
import com.example.week5.service.MemberService;
import com.example.week5.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/posts")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @Autowired
    public PostController(PostService postService, MemberService memberService){
        this.postService = postService;
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<PostDto> registerPost(@RequestBody PostDto postDto) {
        Post post = new Post();
        post.setMember(memberService.getMemberById(postDto.getMemberId()).orElse(null));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        Post registeredPost = postService.registerPost(post);
        return ResponseEntity.ok(PostDto.from(registeredPost));
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPost(){
        List<Post> posts = postService.getAllPosts();
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            PostDto dto = PostDto.from(post);
            postDtos.add(dto);
        }
        return ResponseEntity.ok(postDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id){
        Optional<Post> postOptional = postService.getPostById(id);
        if (postOptional.isPresent()){
            PostDto dto = PostDto.from(postOptional.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable(name = "id") Long id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable(name = "id") Long id, @RequestBody PostDto postDto) {
        try {
            Post updatePost = postService.updatePost(id, postDto);
            return ResponseEntity.ok(PostDto.from(updatePost));
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

}
