package com.netcracker.learnera.entity.template.lesson;

import com.netcracker.learnera.entity.template.Lesson;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("assignment")
public class Assignment extends Lesson {

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordering asc")
    private List<Question> questions = new ArrayList<>();

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
