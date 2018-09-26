package com.memberservice.service;


import com.memberservice.model.Member;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {


    @Autowired
    MemberService memberService;

    public UUID memberOneId = UUID.randomUUID();
    public UUID memberTwoId = UUID.randomUUID();
    public String memberOneFirstName = "Bharat";
    public String memberOneLastName = "Satija";
    public String memberTwoFirstName = "Ankit";
    public String memberTwoLastName = "Kumar";


    Member memberOne = new Member(memberOneId, memberOneFirstName, memberOneLastName, LocalDate.of(1992, 3, 26), "201010");
    Member memberTwo = new Member(memberTwoId, memberTwoFirstName, memberTwoLastName, LocalDate.of(1992, 3, 26), "201010");


    private void setUp(){
        memberService.save(memberOne);
        memberService.save(memberTwo);
    }

    @After
    public void cleanUp(){
        memberService.deleteMember(memberOneId);
        memberService.deleteMember(memberTwoId);
    }


    @Test
    public void testSaveMember(){
        assertThat(memberService.save(memberOne)).isEqualTo(memberOne);
        assertThat(memberService.save(memberTwo)).isEqualTo(memberTwo);
    }


    @Test
    public void testGetMemberById(){
        setUp();
        assertThat(memberService.findById(memberOneId)
                .get()).isEqualTo(memberOne);

        assertThat(memberService.findById(memberTwoId)
                .get()).isEqualTo(memberTwo);
    }

    @Test
    public void testGetAllMember(){
        setUp();
        assertThat(memberService.findAll()).contains(memberOne, memberTwo);
    }

    @Test
    public void testDeleteMember(){
        setUp();
        memberService.deleteMember(memberOneId);

        Optional<Member> member = memberService.findById(memberOneId);
        assertThat(member.isPresent()).isEqualTo(false);
    }





}
