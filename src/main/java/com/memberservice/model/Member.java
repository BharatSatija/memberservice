package com.memberservice.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
public class Member {


    private String id;
    private String firstName;
    private String lastName;
    @JsonFormat(pattern="yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfBirth;
    private String postalCode;



    public Member(String  id, String firstName, String lastName, LocalDate dateOfBirth, String postalCode) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.postalCode = postalCode;
    }

    public Member(){

    }


    public void updateMemberDetails(Member member){
        this.firstName = member.getFirstName() == null ? this.getFirstName() : member.getFirstName();
        this.setLastName(member.getLastName() == null ? this.getLastName() : member.getLastName());
        this.setDateOfBirth(member.getDateOfBirth() == null ? this.getDateOfBirth() : member.getDateOfBirth());
        this.setPostalCode(member.getPostalCode() == null ? this.getPostalCode() : member.getPostalCode());

    }

}
