package com.sparta.springlv2project.dto.boardDto;

import com.sparta.springlv2project.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final Long commentId;
    private final String username;
    private final String comment;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final Long postId;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.username = comment.getUsername();
        this.comment = comment.getComment();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.postId = comment.getPost().getPostId();
    }
}
