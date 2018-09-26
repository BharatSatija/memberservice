package com.memberservice.service;

import com.memberservice.model.Member;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    private final Map<UUID, Member> memberRepository = new ConcurrentHashMap<>();

    public Member save(Member member) {
        memberRepository.put(member.getId(), member);
        return member;
    }

    public Optional<Member> findById(UUID uuid) {
        return Optional.ofNullable(memberRepository.get(uuid));
    }

    public Member deleteMember(UUID uuid){
        return memberRepository.remove(uuid);
    }

    public List<Member> findAll(){
        return memberRepository.values().stream().collect(Collectors.toList());
    }

}
