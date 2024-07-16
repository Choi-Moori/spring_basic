package com.beyond.basic.controller;

import com.beyond.basic.domain.*;
import com.beyond.basic.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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
    public ResponseEntity<List<CommonResDto>> findMemberList() {
        return memberService.memberList();
    }

    /**
     * 회원 상세 조회
     */
    @GetMapping("/member/detail/{id}")
    public ResponseEntity<Object> memberDetail(@PathVariable Long id) {
        try {
            CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "OK",memberService.memberDetail(id));
            return new ResponseEntity<>(commonResDto, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(new CommonErrorDto(HttpStatus.NOT_FOUND, "NOT_FOUND"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/member/create")
//    Json 으로 받으려면 @RequestBody로 받아야 한다.
    public String createMemberPost(@RequestBody MemberReqDto dto) {
        try {
            memberService.memberCreate(dto);
            return HttpStatus.OK.toString(); // 잘 등록됐다고 ok 출력
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST.toString();
        }
    }

//    수정은 2가지 요청방식 : PUT, PATCH 요청
//    patch요청은 부분수정, put요청은 덮어쓰기.
    @PatchMapping("/member/pw/update")
    public String memberPwUpdate(@RequestBody MemberUpdateDto dto){
        memberService.pwUpdate(dto);
        return "ok";
    }

    @DeleteMapping("/member/delete/{id}")
    public String memberDelete(@PathVariable Long id){
        memberService.memberDelete(id);
        return "ok";
    }

}