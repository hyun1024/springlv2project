package com.sparta.springlv2project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.springlv2project.dto.boardDto.PostRequestDto;
import io.jsonwebtoken.Claims;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.sparta.springlv2project.jwt.JwtUtil.AUTHORIZATION_KEY;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="post")
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String contents;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @OrderBy("createdAt DESC")
    private List<Comment> commentList= new ArrayList<>();

    public Post(PostRequestDto postRequestDto, Claims userInfo) {
        this.username = userInfo.getSubject();
        this.subject = postRequestDto.getSubject();
        this.contents = postRequestDto.getContents();
    }
    public Boolean verifyAuthority(Claims userInfo, String username) {
        String role= userInfo.get("auth", String.class);
        String tokenName = userInfo.getSubject();
        if (!((role!=null && role.equals("ADMIN") || tokenName.equals(username)))) {
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }
        return true;
    }
    public void update(PostRequestDto postRequestDto){
        this.subject = postRequestDto.getSubject();
        this.contents = postRequestDto.getContents();
    }
}
