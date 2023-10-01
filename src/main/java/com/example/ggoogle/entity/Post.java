package com.example.ggoogle.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private SiteUser siteUser;

  @Column(length = 50, nullable = false)
  private String title;

  @Column(length = 500, nullable = false)
  private String link;

  @Column(length = 500, nullable = false)
  private String summary;

  @Column(columnDefinition = "int default 0")
  private Integer likeCount;

  @Column(updatable = false, nullable = false)
  @CreatedDate
  private String createdTime;

  @PrePersist
  public void onPrePersist(){
    this.createdTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
  }

  @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
  List<Comment> commentList = new ArrayList<>();
}
