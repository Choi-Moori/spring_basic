package com.beyond.basic.service;


import com.beyond.basic.domain.*;
import com.beyond.basic.repository.MemberRepository;
import com.beyond.basic.repository.MemberSpringDataJpaRepository;
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
    @Autowired
    public MemberService(MemberSpringDataJpaRepository memberRepository){
        System.out.println("MemberService 시작");
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
        Member member = dto.toEntity();
        memberRepository.save(member);
//        List<Member> savedMember = memberRepository.findAll();
//        System.out.println(savedMember.get(savedMember.size()-1));
//        return savedMember.get(savedMember.size()-1);
    }

    public MemberDetailResDto memberDetail(Long id){
        System.out.println("MemberService[MemberDetail] 시작 id : " + id);
//        Springdata jpa 를 쓰기 위해 optional로 리턴타입 바꾸기
//        Member member = memberRepository.findById(id);

//        중요) 트랜잭션 롤백을 해주기 위해 예외를 던진다. & 클라이언트에게 적절한 메시지 출력.
//        적절한 예외메시지와 상태코드를 주는 것, 예외를 강제 발생시킴으로 적절한 롤백처리 하는 것이 주요 목적들.
//        MemberDetailResDto memberDetailResDto = member.detFromEntity();
//        memberDetailResDto.setId(member.getId());
//        memberDetailResDto.setName(member.getName());
//        memberDetailResDto.setEmail(member.getEmail());
//        memberDetailResDto.setPassword(member.getPassword());
//        LocalDateTime createdTime = member.getCreatedTime();
//        String value = createdTime.getYear()+"년"
//                      +createdTime.getMonthValue()      // getMonth()로 하면 6월인 경우 July가 나온다.
//                      +"월"+createdTime.getDayOfMonth()+"일";
//        memberDetailResDto.setCreatedTime(value);

        Optional<Member> optmember = memberRepository.findById(id);
        Member member = optmember.orElseThrow(()->new EntityNotFoundException("없는 회원 입니다."));
        System.out.println("글쓴이의 쓴글의 개수 " + member.getPosts().size());
        for(Post p : member.getPosts()){
            System.out.println("글의 제목 : " + p.getTitle());
            System.out.println("저자의 이름은 : " +p.getMember().getName());
        }
        return member.detFromEntity()/* memberDetailResDto */;
    }

    public List<MemberResDto> memberList(){
        System.out.println("MemberService[memberList] 시작");
        List<Member> memberList = memberRepository.findAll();
        List<MemberResDto> memberResDtos = new ArrayList<>();

        for(Member member : memberList){
//            MemberResDto dto = member.fromListEntity();
//            dto.setId(member.getId());
//            dto.setEmail(member.getEmail());
//            dto.setName(member.getName());
            memberResDtos.add(member.listFromEntity());
        }
        return memberResDtos;
    }

    public void pwUpdate(MemberUpdateDto dto){
        Member member = memberRepository.findById(dto.getId()).orElse(null);

    }
}
