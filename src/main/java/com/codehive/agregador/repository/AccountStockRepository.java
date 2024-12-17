package com.codehive.agregador.repository;

import com.codehive.agregador.entity.AccountStock;
import com.codehive.agregador.entity.AccountStockId;
import com.codehive.agregador.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
