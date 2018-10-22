package com.netcracker.learnera.entity.template.lesson;

import com.netcracker.learnera.entity.template.Lesson;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "assignments")
@PrimaryKeyJoinColumn(name = "lesson_id")
public class Assignment extends Lesson {

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
