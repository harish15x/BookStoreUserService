package com.bridgelabz.bookstore.model;

import com.bridgelabz.bookstore.dto.UserDTO;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bookstoretable")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String kyc;
    private String password;
    private String emailId;
    private Boolean verify;
    private long otp;
    private LocalDateTime dob;
    private LocalDateTime updatedDate;
    private LocalDateTime registeredDate;
    private LocalDateTime purchaseDate;
    private LocalDateTime expiryDate;


    public UserModel(UserDTO userDTO){
       this.firstName = userDTO.getFirstName();
       this.lastName = userDTO.getLastName();
       this.kyc = userDTO.getKyc();
       this.password  = userDTO.getPassword();
       this.emailId = userDTO.getEmailId();
    }

    public UserModel(){

    }
}
