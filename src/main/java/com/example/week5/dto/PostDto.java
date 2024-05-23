package com.example.week5.dto;

import com.example.week5.domain.Member;
import com.example.week5.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {
    private String title;
    private String content;
    private Long memberId;

    public static PostDto from(Post post){
        PostDto postDto = new PostDto();
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setMemberId(post.getMember().getId());
        return postDto;
    }

}
