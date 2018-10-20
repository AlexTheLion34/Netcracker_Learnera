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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Float getPoints() {
        return points;
    }

    public void setPoints(Float points) {
        this.points = points;
    }

    public Float getPenalty() {
        return penalty;
    }

    public void setPenalty(Float penalty) {
        this.penalty = penalty;
    }

    public List<AssignmentAttempt> getAttempts() {
        return attempts;
    }

    public void setAttempts(List<AssignmentAttempt> attempts) {
        this.attempts = attempts;
    }
}
