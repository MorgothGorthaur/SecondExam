package com.example.demo.dto;

import com.example.demo.model.Teacher;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public TeacherDto toTeacherDto(Teacher teacher) {
        return new TeacherDto(teacher.getName(), teacher.getPhone(), teacher.getSkype());
    }

    public TeacherWithGroupDto toTeacherWithGroupDto(Teacher teacher) {
        var group = teacher.getGroup();
        return new TeacherWithGroupDto(teacher.getName(), teacher.getPhone(), teacher.getSkype(), group != null ? group.getName() : "without group");
    }
}
