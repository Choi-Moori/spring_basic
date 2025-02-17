package com.beyond.basic.service;


import com.beyond.basic.domain.*;
import com.beyond.basic.repository.MemberRepository;
import com.beyond.basic.repository.MemberSpringDataJpaRepository;
import com.beyond.basic.repository.MyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

//    input값의 검증 및 실질적인 비지니스 로직은 서비스 계층에서 수행
@Service // 서비스 계층임을 표현함과 동시에 싱글톤객체로 생성.
//Transactional어노테이션을 통해 모든 메서드에 트랜잭션이 적용되고(각 메서드마다 하나의 트랜잭션으로 묶음),
//만약 예외 발생시 롤백처리 자동화.
@Transactional
public class MemberService {
//    다형성 설계
    private final MyMemberRepository memberRepository;
    @Autowired
    public MemberService(MyMemberRepository memberRepository){
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

    public void memberCreate(MemberReqDto dto){
        System.out.println("MemberService[memberCreate] 시작");
        if(dto.getPassword().length()<8){
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }
        Member member = dto.toEntity();
        if(memberRepository.findByEmail(dto.getEmail()).isPresent())
            throw new IllegalArgumentException("중복된 이메일입니다.");
        memberRepository.save(member);
//        Transactional 롤백처리 테스트
//        if(member.getName().equals("kim")) {
//            throw new IllegalArgumentException("잘못된 입력입니다.");
//        }
    }

    public MemberDetailResDto memberDetail(Long id) {
        System.out.println("MemberService[MemberDetail] 시작 id : " + id);
        Optional<Member> optmember = memberRepository.findById(id);
        Member member = optmember.orElseThrow(() -> new EntityNotFoundException("없는 회원 입니다."));
        System.out.println("글쓴이의 쓴글의 개수 " + member.getPosts().size());
        for (Post p : member.getPosts()) {
            System.out.println("글의 제목 : " + p.getTitle());
            System.out.println("저자의 이름은 : " + p.getMember().getName());
        }
        return member.detFromEntity();
    }
    public List<MemberResDto> memberList() {

        List<Member> memberList = memberRepository.findAll();

        List<MemberResDto> memberResDtoList = new ArrayList<>();
        int j = 0;
        for (Member member : memberList) {
//            memberResDtoList.add(new MemberResDto());
//            memberResDtoList.get(j).setId(member.getId());
//            memberResDtoList.get(j).setName(member.getName());
//            memberResDtoList.get(j).setEmail(member.getEmail());

            MemberResDto memberResDto = member.listFromEntity();
            memberResDtoList.add(memberResDto);
            j++;

            System.out.println(memberResDto);
        }
        return memberResDtoList;
    }

    public ResponseEntity<List<CommonResDto>> memberResList(){
        System.out.println("MemberService[memberList] 시작");
        List<Member> memberList = memberRepository.findAll();
        List<CommonResDto> memberResDtos = new ArrayList<>();

        for(Member member : memberList){
            memberResDtos.add(new CommonResDto(HttpStatus.OK, "OK", member.listFromEntity()));
        }
        return new ResponseEntity<>(memberResDtos, HttpStatus.OK);
    }

    public void pwUpdate(MemberUpdateDto dto){
        Member member = memberRepository.
                        findById(dto.getId()).
                        orElseThrow(()->new EntityNotFoundException("member is not found"));
        member.updatePw(dto.getPassword());
//        기존 객체를 조회후 수정한 다음에 save 시에는 jpa가 자동으로 update 실행을 한다.
//        추가와 수정이 모두 save다 하지만 그 전에 findbyId로 찾아와야함.
//        고놈 자식 똑똑하구만.
        memberRepository.save(member);
    }

    public void memberDelete(Long id){
        Member member = memberRepository.
                        findById(id).
                        orElseThrow(()->new EntityNotFoundException("member is not found"));
        memberRepository.delete(member); // 완전삭제.

//        member.updateDelYn("Y");
//        memberRepository.save(member);
    }
}
