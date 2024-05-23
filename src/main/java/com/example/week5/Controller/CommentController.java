package com.example.week5.Controller;

import com.example.week5.domain.Comment;
import com.example.week5.domain.Post;
import com.example.week5.dto.CommentDto;
import com.example.week5.dto.PostDto;
import com.example.week5.service.CommentService;
import com.example.week5.service.MemberService;
import com.example.week5.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final MemberService memberService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService, MemberService memberService){
        this.commentService = commentService;
        this.postService = postService;
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> registerComment(@RequestBody CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setPost(postService.getPostById(commentDto.getPostId()).orElse(null));
        comment.setMember(memberService.getMemberById(commentDto.getMemberId()).orElse(null));
        comment.setContent(commentDto.getContent());

        Comment registeredComment = commentService.registerComment(comment);
        return ResponseEntity.ok(CommentDto.from(registeredComment));
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComment(){
        List<Comment> comments = commentService.getAllComments();
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto dto = CommentDto.from(comment);
            commentDtos.add(dto);
        }
        return ResponseEntity.ok(commentDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(name = "id") Long id){
        Optional<Comment> commentOptional = commentService.getCommentById(id);
        if (commentOptional.isPresent()){
            CommentDto dto = CommentDto.from(commentOptional.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentByPostId(@PathVariable(name = "postId") Long postId){
        List<Comment> comments = commentService.getCommentByPostId(postId);
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto dto = CommentDto.from(comment);
            commentDtos.add(dto);
        }
        return ResponseEntity.ok(commentDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable(name = "id") Long id){
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(name = "id") Long id, @RequestBody CommentDto commentDto){
        try {
            Comment updateComment = commentService.updateComment(id, commentDto);
            return ResponseEntity.ok(CommentDto.from(updateComment));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}