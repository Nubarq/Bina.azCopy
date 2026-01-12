package com.example.demo.repository;

import com.example.demo.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card,Integer> {
    Optional<Card> findByCardNumber(String cardNumber);

    List<Card> findByIsActiveTrueAndExpirationDateBefore(Date currentDate);
}
