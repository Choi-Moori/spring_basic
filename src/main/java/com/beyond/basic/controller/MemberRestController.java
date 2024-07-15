package com.beyond.basic.controller;

import com.beyond.basic.domain.MemberDetailResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.domain.MemberUpdateDto;
import com.beyond.basic.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 화면을 출력하는 것이 아니라 json 데이터를 전송하기
 * */
@RestController //RestController의 경우 모든 메서드에 Responsbody가 붙는 효과가 발생한다.
@RequestMapping("/rest")
public class MemberRestController {

    private final MemberService memberService;

    @Autowired
    public MemberRestController(MemberService memberService) {
        this.memberService = memberService; // 이름이 같아서 (다형성x) this 사용
    }

    /**
     * 회원 목록 조회
     */
    @GetMapping("/member/list")
    public List<MemberResDto> findMemberList() {
        List<MemberResDto> memberResDtoList = memberService.memberList();
        return memberResDtoList;
    }

    /**
     * 회원 상세 조회
     */
    @GetMapping("/member/detail/{id}")
    public MemberDetailResDto memberDetail(@PathVariable Long id) {
        return memberService.memberDetail(id);
    }

    @PostMapping("/member/create")
//    Json 으로 받으려면 @RequestBody로 받아야 한다.
    public String createMemberPost(@RequestBody MemberReqDto dto) {
        try {
            memberService.memberCreate(dto);
            return "ok"; // 잘 등록됐다고 ok 출력
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "error 발생 ‼️";
        }
    }

//    수정은 2가지 요청방식 : PUT, PATCH 요청
//    patch요청은 부분수정, put요청은 덮어쓰기.
    @PatchMapping("/member/pw/update")
    public String memberPwUpdate(@RequestBody MemberUpdateDto dto){
        memberService.pwUpdate(dto);
        return "ok";
    }

}