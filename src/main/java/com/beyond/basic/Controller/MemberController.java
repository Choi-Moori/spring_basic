package com.beyond.basic.Controller;

import com.beyond.basic.Domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberController {
    
//    회원 목록 조회
    @GetMapping("/member/list")
    public String memberList(){
        return "Member/memberList";
    }

    @GetMapping("/member/{id}")
//    int 또는 long으로 받을 경우 스프링에서 형변환(String->Long)
    public String memberDetail(@PathVariable Long id, Model model){
//        model.addAttribute("id", id);
        return "Member/memberDetail";
    }

    @GetMapping("/member/create")
    public String memberCreate(){
        return "Member/memberCreate";
    }

    @PostMapping("/member/create")
    @ResponseBody
    public String memberCreatePost(@ModelAttribute Member member){
        System.out.println(member);
        return "ok";
    }
}