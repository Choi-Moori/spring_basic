package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyMemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email); // 직접 구현할 수 있음 -> 런타임 시점에서 만들어짐

    List<Member> findByName(String name);
    
//    jqpl문법을 통한 raw쿼리 작성시 컴파일 타임에서 오류 체크
//    @Query("select m from Member m")
//    List<Member> myFindAll();
}