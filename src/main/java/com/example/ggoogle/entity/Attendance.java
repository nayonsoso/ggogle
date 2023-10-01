package com.example.ggoogle.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Attendance {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private SiteUser siteUser;

  //TODO: "yy-mm" 형식만 저장되게 할 수는 없나?
  @Column(updatable = false, nullable = false, length = 50)
  private String stage;

  @Column(updatable = false, nullable = false)
  private int week;

  @Column(updatable = false, nullable = false)
  private int postCount;

  @Column(updatable = false, nullable = false)
  private int commentCount;

  @Column(updatable = false, nullable = false)
  private int fine;
}