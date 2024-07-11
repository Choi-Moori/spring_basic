package com.beyond.basic.Repository;


import com.beyond.basic.Domain.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

//해당 클래스가 Repository계층임을 표현함과 동시에 Singleton(싱글톤)객체로 생성.
//싱글톤(Singleton) : 단 하나의 객체로 만들겠다.
@Repository
public class MemberMemoryRepository implements MemberRepository {

    private final List<Member> memberList;


    public MemberMemoryRepository(){
        this.memberList = new ArrayList<>();
    }

    @Override
    public Member save(Member member) {
        memberList.add(member);
        return member;
    }

    @Override
    public List<Member> findAll() {
        return memberList;
    }

    @Override
    public Member findById(Long id) {
        return null;
    }
}
