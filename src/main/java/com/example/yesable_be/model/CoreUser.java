package com.example.yesable_be.model;


import com.example.yesable_be.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="usertype")
@Data
public class CoreUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

   
   // private String usertype; //개인, 기업회원 구분 DiscriminatorColumn으로 대체

    
    private String email;
    private String phoneNumber;
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;



    private LocalDate dateOfBirth;



}
