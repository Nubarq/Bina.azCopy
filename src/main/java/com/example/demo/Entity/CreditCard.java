package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String cardNumber;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    /* @Enumerated(EnumType.STRING)
    private CardType cardType;
     */

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id_credit")
    @JsonBackReference
    private User user;

    private boolean isActive;

    //private double balance;

    /*
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;


     */
    public CreditCard() {

    }
    /* public CreditCard() {
        //this.cardNumber = generateUniqueCardNumber();
        //this.expirationDate = calculateExpirationDate();
        this.isActive = true;
        //this.balance = 0.0;
        this.createdAt = new Date();

    }

     */

    public boolean checkCardIsActive() {
        return isActive && expirationDate.after(new Date());
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

/*
    private String generateUniqueCardNumber() {
        // Fixed prefix for the card number
        String prefix = "88186818";

        // Generate a unique portion using UUID
        String uniquePortion = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);

        // Concatenate the prefix and unique portion to create the full card number
        return prefix + uniquePortion;
    }


 */
    /* public double updateBalance(Transaction transaction, double transactionAmount) {
        double currentBalance = getBalance();
        double resultBalance = currentBalance;
//        double transactionAmount = transaction.getAmount();

        // Adjust for withdrawals
        if (TransactionType.WITHDRAWAL.equals(transaction.getTransactionType())) {
            transactionAmount *= -1;
            resultBalance = transactionAmount + currentBalance;
        } else if (TransactionType.DEPOSIT.equals(transaction.getTransactionType())) {
            transactionAmount *= 1;
            resultBalance = transactionAmount + currentBalance;
        }
        return resultBalance;
    }
*/
    /*
    private Date calculateExpirationDate() {
        // Implement your logic to calculate the expiration date, for example adding 3 years to the current date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 3);
        return calendar.getTime();
    }


     */

   /* public void addCreditBalanceToCardBalance(double amount){
        balance = balance+amount;
    }
    public void addBalanceTransaction(double amount){
        balance = balance + amount;
    }

    */
}