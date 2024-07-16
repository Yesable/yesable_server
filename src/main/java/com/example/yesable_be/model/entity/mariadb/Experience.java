package com.example.yesable_be.model.entity.mariadb;

import com.example.yesable_be.enums.Experiencetype;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "private_user_id")
    private PrivateUser privateUser;

    @Enumerated(EnumType.STRING)
    private Experiencetype experiencetype;
    private String companyname;


    private LocalDate startDate;
    private LocalDate endDate;
    private String department; //직무 종류
    private String jobDescription; //주요 업무 내용


}
