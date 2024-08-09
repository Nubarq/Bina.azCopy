package com.example.demo.Repository;

import com.example.demo.Entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """ 
        SELECT * FROM token t INNER JOIN user ON t.user_id_ = user.user_id WHERE user.user_id = :user_id\s
        AND (t.expired = 0 OR t.revoked = 0);
      """, nativeQuery = true)
    List<Token> findAllValidTokenByUser(Integer user_id);

    Optional<Token> findByToken(String token);
}
