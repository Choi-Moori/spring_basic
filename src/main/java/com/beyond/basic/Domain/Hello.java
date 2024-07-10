package com.beyond.basic.Domain;

import lombok.*;

import java.io.File;

//@Getter
//@Setter // 롬복라이브러리를 통해 getter, setter 어노테이션 사용.
@Data // getter,setter,toSTring 등을 포함
//@NoArgsConstructor : 기본생성자를 만드는 어노테이션
//@AllArgsConstructor //: 모든 매개변수를 사용한 생성자를 만드는 어노테이션
public class Hello {
    private String Name;
    private String Email;
    private String password;

//    @Override
//    public String toString(){
//        return "name : " + this.Name + " Email : " + this.Email + " Password : " +this.password;
//    }
}
