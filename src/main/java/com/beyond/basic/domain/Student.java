package com.beyond.basic.domain;

import lombok.Data;

import java.util.List;

@Data
public class Student {
    private String name;
    private String email;
    private List<Student.Scores> grades;

    @Data
    static class Scores{
        private String className;
        private String point;
    }
}
