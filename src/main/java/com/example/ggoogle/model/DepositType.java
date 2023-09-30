package com.example.ggoogle.model;

import lombok.Getter;

@Getter
public enum DepositType {
  // 입금, 출금, 벌금, 정산
  DEPOSIT, WITHDRAWAL, FINE, SETTLEMENT
}
