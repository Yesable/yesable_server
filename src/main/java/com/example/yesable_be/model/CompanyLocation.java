package com.example.yesable_be.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
public class CompanyLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "company_user_id")
    private CompanyUser companyuser;


    private String statename; //시도명
    private String cityname; //시군구명
    private String legaltownname; //법정읍면동명
    private String legalvilagename; //법정리명
    private String streetname; //도로명
    private Integer buildingnumber; //건물본번
    private Integer buildingsubnumber; //건물부번
    private String cicycountybuildingname; //시군구건물명
    
    private Boolean isapartment; //공동건물 여부
    private Integer apartmentnumber; //공동건물호수
            
}
