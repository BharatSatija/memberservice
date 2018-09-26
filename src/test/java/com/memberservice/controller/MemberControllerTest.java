package com.memberservice.controller;


import com.memberservice.model.Member;
import com.memberservice.service.MemberService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    MemberService memberService;


    public UUID memberOneId = UUID.randomUUID();
    public UUID memberTwoId = UUID.randomUUID();
    public String memberOneFirstName = "Bharat";
    public String memberOneLastName = "Satija";
    public String memberTwoFirstName = "Ankit";
    public String memberTwoLastName = "Kumar";

    public UUID memberThreeId = UUID.randomUUID();
    public String memberThreeFirstName = "Amit";
    public String memberThreeLastName = "Singh";



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
    public void testCreateMemberApi() throws Exception{

        String json = "{\"firstName\" : \""+memberOneFirstName+"\", \"lastName\" : \""+memberOneLastName+"\", " +
                "\"dateOfBirth\" : \"1992-03-26\", \"postalCode\" : \"13359\" }";

        mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testCreateMemberApiInvalidDate() throws Exception{

        String json = "{\"firstName\" : \""+memberOneFirstName+"\", \"lastName\" : \""+memberOneLastName+"\", \"dateOfBirth\" : \"1992-26-03\", \"postalCode\" : \"13359\" }";

        mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid date format"));
    }


    @Test
    public void testGetMemberApi() throws Exception {
        setUp();

        mockMvc.perform(get("/api/members/" + memberOneId.toString())
                .accept(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.lastName").exists())
                .andExpect(jsonPath("$.id").value(memberOneId.toString()))
                .andExpect(jsonPath("$.firstName").value(memberOneFirstName))
                .andExpect(jsonPath("$.lastName").value(memberOneLastName));
    }


    @Test
    public void testInvalidGetMemberApi() throws Exception {
        setUp();

        mockMvc.perform(get("/api/members/" + memberThreeId.toString())
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    public void testGetAllMemberApi() throws Exception {
        setUp();
        mockMvc.perform(get("/api/members/")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)));
    }


    @Test
    public void testUpdateMemberApi() throws Exception {
        setUp();
        String json = "{\"firstName\" : \""+memberThreeFirstName+"\", \"lastName\" : \""+memberThreeLastName+"\", " +
                "\"dateOfBirth\" : \"1992-03-26\", \"postalCode\" : \"13359\" }";

        mockMvc.perform(put("/api/members/"+memberOneId.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.lastName").exists())
                .andExpect(jsonPath("$.id").value(memberOneId.toString()))
                .andExpect(jsonPath("$.firstName").value(memberThreeFirstName))
                .andExpect(jsonPath("$.lastName").value(memberThreeLastName));
    }


    @Test
    public void testInvalidUpdateMemberApi() throws Exception {
        setUp();
        String json = "{\"firstName\" : \""+memberThreeFirstName+"\", \"lastName\" : \""+memberThreeLastName+"\", \"dateOfBirth\" : \"1992-03-26\", \"postalCode\" : \"13359\" }";

        mockMvc.perform(put("/api/members/" + memberThreeId.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    public void testDeleteMemberApi() throws Exception {
        setUp();

        mockMvc.perform(delete("/api/members/" + memberOneId.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void testInvalidDeleteMemberApi() throws Exception {
        setUp();

        mockMvc.perform(delete("/api/members/" + memberThreeId.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
