package com.example.ggoogle.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
일대일 연관관계를 학습하기 위해서 만든 객체
* */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserProfile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 500)
  private String introduction;

  @Column
  private String github;

  @Column
  private String blog;

  @Column
  private String linkedIn;

  @OneToOne
  private SiteUser siteUser;
}