package com.example.demo.Repository;

import com.example.demo.Entity.CreditCard;
import com.example.demo.Entity.Property;
import com.example.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<CreditCard,Long> {
    Optional<CreditCard> findByCardNumber(String cardNumber);

    Optional<CreditCard> findByUser_UserId(int userId);

    List<CreditCard> findByExpirationDateBefore(LocalDate date);

}