package com.memberservice.service;


import com.memberservice.model.Member;
import java.util.*;

public interface MemberService {

    Member save(Member member);

    Optional<Member> findById(UUID uuid);

    Member deleteMember(UUID uuid);

    List<Member> findAll();
}
