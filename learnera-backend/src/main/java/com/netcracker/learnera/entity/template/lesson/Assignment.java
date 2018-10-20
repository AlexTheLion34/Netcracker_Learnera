package com.netcracker.learnera.entity.template.lesson;

import com.netcracker.learnera.entity.template.Lesson;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Embeddable
public abstract class Assignment extends Lesson {

    @Column(name = "answer")
    protected String answer;

    @Column(name = "points")
    protected Float points;

    @Column(name = "penalty")
    protected Float penalty;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<AssignmentAttempt> attempts = new ArrayList<>();

}
