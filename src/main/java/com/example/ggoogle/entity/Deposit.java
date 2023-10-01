package com.example.ggoogle.entity;

import com.example.ggoogle.model.DepositType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Deposit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // @Comment("입금/출금/벌금/정산 된 액수, 부호 있음")
  @Column(updatable = false, nullable = false)
  private int amount;

  @Column(updatable = false, nullable = false)
  @CreatedDate
  private String createdTime;

  @Column(columnDefinition = "int unsigned", updatable = false, nullable = false)
  private int balance;

  @Column(nullable = false, updatable = false)
  @Enumerated(EnumType.STRING)
  private DepositType depositType;

  @PrePersist
  public void onPrePersist(){
    this.createdTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
  }

  @ManyToOne
  private SiteUser user;
}
