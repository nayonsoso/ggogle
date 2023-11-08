package com.example.ggoogle.repository;

import com.example.ggoogle.entity.Deposit;
import com.example.ggoogle.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {

}
