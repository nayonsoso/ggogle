package com.example.ggogle.entity;

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
  @Column(updatable = false, nullable = false)
  private String stage;

  @Column(updatable = false, nullable = false)
  private Integer week;

  @Column(updatable = false, nullable = false)
  private Integer postCount;

  @Column(updatable = false, nullable = false)
  private Integer commentCount;

  @Column(updatable = false, nullable = false)
  private Integer fine;
}