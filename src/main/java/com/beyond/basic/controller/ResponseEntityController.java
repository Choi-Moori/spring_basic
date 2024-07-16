package com.beyond.basic.controller;

import com.beyond.basic.domain.CommonResDto;
import com.beyond.basic.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/response/entity")
public class ResponseEntityController {
//    Case1. @ResponseStatus 어노테이션 방식.

    @GetMapping("/annotation1")
    @ResponseStatus(HttpStatus.OK) // Header에 들어감
    public String annotation1(){
        return "ok";
    }

    @GetMapping("/annotation2")
    @ResponseStatus(HttpStatus.CREATED) // Header에 들어감.
    public Member annotation2(){
//        (가정) 객체 생성후 DB 저장 성공
        Member member = new Member("hong", "hogn@naver.com", "12341234");

        return member;
    }
    
    
//    Case2. 메서드 체이닝 방식 : ResponseEntity의 클래스메서드 사용
//    전부다 알아야 한다 많이 사용됨. 외울 필요는 없지만
    @GetMapping("/chaining1")
    public ResponseEntity<Member> chaining1(){
        Member member = new Member("hong", "hogn@naver.com", "12341234");
        return ResponseEntity.ok(member);
    }

    @GetMapping("/chaining2")
    public ResponseEntity<Member> chaining2(){
        Member member = new Member("hong", "hogn@naver.com", "12341234");
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @GetMapping("/chaining3")
    public ResponseEntity<Member> chaining3(){
        return ResponseEntity.notFound().build();
    }

//    case3. ResponseEntity객체를 직접 custom하여 생성하는 방식
    @GetMapping("/custom1")
    public ResponseEntity<Member> custom1(){
        Member member = new Member("hong", "hogn@naver.com", "12341234");
        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }

    @GetMapping("/custom2")
    public ResponseEntity<CommonResDto> custom2(){
        Member member = new Member("hong", "hogn@naver.com", "12341234");
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "ok", member);
        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
    }


}