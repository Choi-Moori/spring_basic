package com.beyond.basic.Domain;

import lombok.Data;

@Data
public class Member {
    private Long id = 0L;
    private String name;
    private String email;
    private String password;
}
