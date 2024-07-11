package com.beyond.basic.Repository;

import com.beyond.basic.Domain.Member;

import java.util.List;

public class MemberJpaRepository implements MemberRepository{
    @Override
    public Member save(Member member) {

        return null;
    }

    @Override
    public List<Member> findAll() {
        return List.of();
    }

    @Override
    public Member findById(Long id) {
        return null;
    }
}
