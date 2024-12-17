package com.codehive.agregador.repository;

import com.codehive.agregador.entity.Account;
import com.codehive.agregador.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
}
