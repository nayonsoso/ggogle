package com.example.ggogle.entity;

import com.example.ggogle.model.DepositType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
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

  @Comment("입금/출금/벌금/정산 된 액수 - signed int")
  @Column(columnDefinition = "default int 0", updatable = false, nullable = false)
  private Integer amount;

  @Column(updatable = false, nullable = false)
  @CreatedDate
  private String createdTime;

  @Column(columnDefinition = "unsigned int", updatable = false)
  private Integer balance;

  @Column(nullable = false, updatable = false)
  @Enumerated(EnumType.STRING)
  private DepositType depositType;

  @PrePersist
  public void onPrePersist(){
    this.createdTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
  }
}
