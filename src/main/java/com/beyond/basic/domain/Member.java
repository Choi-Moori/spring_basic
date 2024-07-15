package com.beyond.basic.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

//@Data
@Getter
//entity어노테이션을 통해 엔티티매니저에게 객체관리를 위임
//해당 클래스명으로 테이블 및 컬럼을 자동생성하고 각종 설정정보 위임
@Entity
@NoArgsConstructor // 기본생성자는 JPA에서 필수
public class Member {
//    Jpa 를 통할 때는 @Id가 필수이다.
    @Id  // pk 설정
//    indentity : auto_increment설정
//    auto : jpa 자동으로 적절한 전략을 선택하도록 맡기는것.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // long은 bigint로 변환
//    String은 Varchar(255)로 기본으로 변환. name 변수명이 name 컬럼명으로 변환
    private String name;
//    nullable=false : notnull 제약조건.
//    unique = true : unique 제약조건.
    @Column(nullable = false,length = 50, unique = true)
    private String email;
    //    @Column(name="pw") 이렇게 할 수 있으나, 컬럼명과 변수명을 일치시키는 것이 혼선을 줄일 수 있다.
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Post> posts;

//    LocalDateTime => DB에는 Datetime이 들어간다.
//    CamelCase로 작성하면 _(언더바)로 들어간다.
//    createTime == create_time
    @CreationTimestamp // DB에는 current_timestamp가 생성되지 않음.
    private LocalDateTime createdTime;

    @UpdateTimestamp // 값을 수정할 때마다. 시간이 바뀐다.
    private LocalDateTime updateTime;

    public Member(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;

    }

    public MemberResDto listFromEntity(){
        return new MemberResDto(id, name, email);
    }

    public MemberDetailResDto detFromEntity(){
        LocalDateTime createdTime = this.getCreatedTime();
        String value = createdTime.getYear()+"년"
                +createdTime.getMonthValue()+"월"    // getMonth()로 하면 6월인 경우 July가 나온다.
                +createdTime.getDayOfMonth()+"일";

        return new MemberDetailResDto(id, name, email, password, value);
    }
}
