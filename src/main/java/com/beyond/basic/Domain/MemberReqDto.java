package com.beyond.basic.Domain;

import lombok.Data;

@Data
public class MemberReqDto {
    private String name;
    private String email;
    private String password;
}
