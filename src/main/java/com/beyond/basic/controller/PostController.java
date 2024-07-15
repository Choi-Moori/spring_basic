package com.beyond.basic.controller;

import com.beyond.basic.domain.PostResDto;
import com.beyond.basic.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService){
        System.out.println("PostController 시작 ");
        this.postService = postService;
    }

    @GetMapping("/post/list")
    @ResponseBody
    public List<PostResDto> postList(){
        System.out.println("PostController[postList] 시작 ");
        return postService.postList();
    }
}
