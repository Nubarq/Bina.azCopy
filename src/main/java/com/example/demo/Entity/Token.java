package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "token")
public class Token implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Integer token_id;

    @Column(name = "token")
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_type")
    public TokenType token_type = TokenType.BEARER;

    @Setter
    @Getter
    @Column(name = "revoked")
    public int revoked;

    @Setter
    @Getter
    @Column(name = "expired")
    public int expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_")
    @JsonBackReference
    public User user;

}
