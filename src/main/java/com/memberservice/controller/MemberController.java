package com.memberservice.controller;


import com.memberservice.model.Member;
import com.memberservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping(value = "/api")
public class MemberController {

    @Autowired
    MemberService memberService;


    @RequestMapping(value = "/members",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> createMember(@RequestBody Member member){

        member.setId(UUID.randomUUID().toString());
        Member savedMember = memberService.save(member);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{memberId}")
                .buildAndExpand(member.getId()).toUri();

        return ResponseEntity.created(location).body(savedMember);
    }


    @RequestMapping(value = "/members/{memberId}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getMember(@NotNull @PathVariable(value = "memberId") String memberId){

        Optional<Member> member = memberService.findById(memberId);
        if(member.isPresent())
            return ResponseEntity.ok(member);

        return ResponseEntity.notFound().build();
    }


    @RequestMapping(value = "/members",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getAllMembers() {
        return ResponseEntity.ok(memberService.findAll());
    }



    @RequestMapping(value = "/members/{memberId}",
            method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> updateMember(@NotNull @PathVariable(value = "memberId") String memberId, @RequestBody Member member){

        Optional<Member> existingMember = memberService.findById(memberId);
        return existingMember.map(m -> {
            m.updateMemberDetails(member);
            Member updatedMember = memberService.save(m);
            return ResponseEntity.ok().body(updatedMember);
        }).orElse(ResponseEntity.notFound().build());
    }



    @RequestMapping(value = "/members/{memberId}",
            method = RequestMethod.DELETE,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> deleteMember(@NotNull @PathVariable(value = "memberId") String memberId){

        if(memberService.deleteMember(memberId) == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().build();
    }



}
