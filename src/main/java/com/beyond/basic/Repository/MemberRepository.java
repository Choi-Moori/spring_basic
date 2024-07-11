package com.beyond.basic.Repository;

import com.beyond.basic.Domain.Member;

import java.util.List;


public interface MemberRepository {

    Member save(Member member);
    List<Member> findAll();
    Member findById(Long id);

}
