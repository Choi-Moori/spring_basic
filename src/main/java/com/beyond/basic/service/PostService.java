package com.beyond.basic.service;

import com.beyond.basic.domain.Post;
import com.beyond.basic.domain.PostResDto;
import com.beyond.basic.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    @Autowired
    public PostService(PostRepository postRepository){
        System.out.println("PostService 시작 ");
        this.postRepository = postRepository;
    }
    public List<PostResDto> postList(){
        System.out.println("PostService[postList] 시작 ");
        List<Post> postList = postRepository.findAll();
        List<PostResDto> postResDto = new ArrayList<>();

        for(Post post : postList){
            postResDto.add(post.FromEntity());
        }
        return postResDto;
    }
}
