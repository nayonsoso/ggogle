package com.example.ggoogle.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = CascadeType.REMOVE)
  private Post post;

  @ManyToOne
  private SiteUser siteUser;

  @Column(length = 500, nullable = false)
  private String content;

  @Column(updatable = false, nullable = false)
  @CreatedDate
  private String createdTime;

  @PrePersist
  public void onPrePersist(){
    this.createdTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
  }

  // 연관관계 편의 메서드
  public void addCommentTo(Post post){
    this.post = post;
    if(post.getCommentList() == null){
      post.setCommentList(new ArrayList<>());
    }
    post.getCommentList().add(this);
  }
}
