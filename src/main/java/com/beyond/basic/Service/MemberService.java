package com.beyond.basic.Service;


import com.beyond.basic.Domain.Member;
import com.beyond.basic.Domain.MemberReqDto;
import com.beyond.basic.Domain.MemberResDto;
import com.beyond.basic.Repository.MemberJdbcRepository;
import com.beyond.basic.Repository.MemberMemoryRepository;
import com.beyond.basic.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // 서비스 계층임을 표현함과 동시에 싱글톤객체로 생성.
//    input값의 검증 및 실질적인 비지니스 로직은 서비스 계층에서 수행
public class MemberService {
//    최초의 MemberService 가 만들어 질때 객체를 한번만 만들겠다.
    private final MemberRepository memberRepository;

    @Autowired //싱글톤객체를 주입(DI) 받는다라는 것을 의미
    public MemberService(MemberJdbcRepository memoryRepository){
//        싱글톤 객체를 선언했을 때 new 를 사용하면 객체가 2개가 된다.
//        왼쪽 인터페이스명, 오른쪽 구현체(like new ~~~~)
        this.memberRepository = memoryRepository;
    }
//    @Autowired
//    private MemberController memberController;


    // 이렇게 사용해도 가능하다.
//    but 위에와 다르게 인터페이스로 하는 것이 아니라 사용하고자 하는 클래스명으로 객체를 만들어 주어야 한다.
//    ex) 위에는 MemberRepository memberRepository;
//    ex) 밑에는 MemberMemoryRepository memberRepository;
//    @Authwired
//    private final MemberMemoryRepository memberRepository;

    public void memberCreate(MemberReqDto dto){
        if(dto.getPassword().length()<8){
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }
        Member member = new Member();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPassword(dto.getPassword());
        memberRepository.save(member);
    }

    public Member memberDetail(Long id){
        return memberRepository.findById(id);
    }

    public List<MemberResDto> memberList(){
        List<Member> member = memberRepository.findAll();
        List<MemberResDto> resDto = new ArrayList<>();

        int j = 0;
        for(Member i : member){
            resDto.add(new MemberResDto());
            resDto.get(j).setId(i.getId());
            resDto.get(j).setEmail(i.getEmail());
            resDto.get(j).setName(i.getName());
            j++;
        }
        return resDto;
    }
}
