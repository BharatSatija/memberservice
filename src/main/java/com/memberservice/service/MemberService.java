package com.memberservice.service;


import com.memberservice.model.Member;
import java.util.*;

public interface MemberService {

    Member save(Member member);

    Optional<Member> findById(String id);

    Member deleteMember(String id);

    List<Member> findAll();
}
