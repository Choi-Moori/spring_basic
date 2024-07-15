package com.beyond.basic.service;


import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberDetailResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.repository.MemberRepository;
import com.beyond.basic.repository.MemberSpringDataJpaRepository;
import com.beyond.basic.repository.MyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//    input값의 검증 및 실질적인 비지니스 로직은 서비스 계층에서 수행
@Service // 서비스 계층임을 표현함과 동시에 싱글톤객체로 생성.
//Transactional어노테이션을 통해 모든 메서드에 트랜잭션이 적용되고(각 메서드마다 하나의 트랜잭션으로 묶음),
//만약 예외 발생시 롤백처리 자동화.
@Transactional
public class MemberService {

//    다형성 설계
    private final MemberRepository memberRepository;
    public MemberService(MemberSpringDataJpaRepository memberRepository){
        this.memberRepository = memberRepository;
    }

//    최초의 MemberService 가 만들어 질때 객체를 한번만 만들겠다.
//    비다형성 설계
//    private final MyMemberRepository memberRepository;
//    @Autowired //싱글톤객체를 주입(DI) 받는다라는 것을 의미
//    public MemberService(MyMemberRepository memoryRepository){
//        System.out.println("MemberService 시작");
////        싱글톤 객체를 선언했을 때 new 를 사용하면 객체가 2개가 된다.
////        왼쪽 인터페이스명, 오른쪽 구현체(like new ~~~~)
//        this.memberRepository = memoryRepository;
//    }
//    @Autowired
//    private MemberController memberController;


    // 이렇게 사용해도 가능하다.
//    but 위에와 다르게 인터페이스로 하는 것이 아니라 사용하고자 하는 클래스명으로 객체를 만들어 주어야 한다.
//    ex) 위에는 MemberRepository memberRepository;
//    ex) 밑에는 MemberMemoryRepository memberRepository;
//    @Authwired
//    private final MemberMemoryRepository memberRepository;

    public void/*Member*/ memberCreate(MemberReqDto dto){
        System.out.println("MemberService[memberCreate] 시작");
        if(dto.getPassword().length()<8){
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }
        Member member = new Member();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPassword(dto.getPassword());
        memberRepository.save(member);
//        List<Member> savedMember = memberRepository.findAll();
//        System.out.println(savedMember.get(savedMember.size()-1));
//        return savedMember.get(savedMember.size()-1);
    }

    public MemberDetailResDto memberDetail(Long id){
        System.out.println("MemberService[MemberDetail] 시작 id : " + id);

        Optional<Member> optmember = memberRepository.findById(id);
//        Member member = memberRepository.findById(id);

        MemberDetailResDto memberDetailResDto = new MemberDetailResDto();
//        중요) 트랜잭션 롤백을 해주기 위해 예외를 던진다. & 클라이언트에게 적절한 메시지 출력.
//        적절한 예외메시지와 상태코드를 주는 것, 예외를 강제 발생시킴으로 적절한 롤백처리 하는 것이 주요 목적들.
        Member member = optmember.orElseThrow(()->new EntityNotFoundException("없는 회원 입니다."));
        memberDetailResDto.setId(member.getId());
        memberDetailResDto.setName(member.getName());
        memberDetailResDto.setEmail(member.getEmail());
        memberDetailResDto.setPassword(member.getPassword());
        return memberDetailResDto;
    }

    public List<MemberResDto> memberList(){
        System.out.println("MemberService[memberList] 시작");
        List<Member> memberList = memberRepository.findAll();
        List<MemberResDto>memberResDtos = new ArrayList<>();

        for(Member member : memberList){
            MemberResDto dto = new MemberResDto();
            dto.setId(member.getId());
            dto.setEmail(member.getEmail());
            dto.setName(member.getName());
            memberResDtos.add(dto);
        }
        return memberResDtos;
    }
}
