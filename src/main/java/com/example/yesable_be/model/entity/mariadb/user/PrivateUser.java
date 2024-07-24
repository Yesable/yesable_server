package com.example.yesable_be.model.entity.mariadb.user;



import com.example.yesable_be.enums.user.Disabilitytype;
import com.example.yesable_be.enums.user.Educationlevel;
import com.example.yesable_be.enums.user.Interestfield;
import com.example.yesable_be.enums.user.Worktype;
import com.example.yesable_be.model.entity.mariadb.user.CoreUser;
import com.example.yesable_be.model.entity.mariadb.user.Experience;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class PrivateUser extends CoreUser {

    private String username;

    private String location; // 추후 수정

    @ElementCollection
    private Set<Interestfield> interestField; //관심분야 : IT, 서비스, 제조업

    @ElementCollection
    private Set<Worktype> workType; //선호하는 업무 형태, 정규직, 파트타임..

    @ElementCollection
    private Set<String> skills; //자격증

    @Enumerated(EnumType.STRING)
    private Educationlevel education;

    private String personality; //개인 성격

    private String hobbies; // 취미

    private String supportneeds; //지원 요구사항

    @Enumerated(EnumType.STRING)
    private Disabilitytype disabilitytype;

    @OneToMany(mappedBy = "privateUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Experience> experiences;

}
