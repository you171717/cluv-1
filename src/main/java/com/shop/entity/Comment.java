package com.shop.entity;

import com.shop.dto.CommentFormDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name="comment")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @Column(name="comment_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "inquiry_id")
    private Inquiry inquiry;

    private String content;


    public static Comment createComment(Inquiry inquiry,CommentFormDto commentFormDto){
        Comment comment=new Comment();
        comment.setContent(commentFormDto.getContent());
        comment.setInquiry(inquiry);
        return comment;
    }

    @Builder
    public Comment(Long id, String content, Inquiry inquiry) {
        this.id=id;
        this.content = content;
        this.inquiry=inquiry;
    }

}