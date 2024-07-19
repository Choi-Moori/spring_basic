package com.beyond.basic.domain;

import lombok.*;

//@Getter
//@Setter // 롬복라이브러리를 통해 getter, setter 어노테이션 사용.
@Data // getter,setter,toSTring 등을 포함
@NoArgsConstructor //: 기본생성자를 만드는 어노테이션
@AllArgsConstructor //: 모든 매개변수를 사용한 생성자를 만드는 어노테이션
public class Hello {
    private String name;
    private String email;
    private String password;

    public Hello(HelloBuilder helloBuilder){
        this.name = helloBuilder.name;
        this.email = helloBuilder.email;
        this.password = helloBuilder.password;
    }

//    @Override
//    public String toString(){
//        return "name : " + this.Name + " Email : " + this.Email + " Password : " +this.password;
//    }

    public static HelloBuilder builder(){
        return new HelloBuilder();
    }
//    Builder패턴 직접 구현.
//    빌더 적용 대상 생성자가 필요
    public static class HelloBuilder{
        private String name;
        private String email;
        private String password;
        public HelloBuilder name(String name){
            this.name = name;
            return this;
        }
        public HelloBuilder email(String email){
            this.email = email;
            return this;
        }
        public HelloBuilder password(String password){
            this.password = password;
            return this;
        }
        public Hello build(){
            return new Hello(this);
        }
    }
}
