package com.example.week5.dto;

import com.example.week5.domain.Comment;
import com.example.week5.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private String content;
    private Long postId;
    private Long memberId;

    public static CommentDto from(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setContent(comment.getContent());
        commentDto.setPostId(comment.getPost().getId());
        commentDto.setMemberId(comment.getMember().getId());
        return commentDto;
    }
}
