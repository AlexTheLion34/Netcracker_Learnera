package com.netcracker.learnera.entity.template.lesson;

import javax.persistence.*;

@Entity
@Table(name = "mc_assignment_variants")
public class MultipleChoiceVariant {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id")
    private MultipleChoiceAssignment assignment;

    private String choiceText;

    private Boolean isCorrect;

    public MultipleChoiceVariant() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MultipleChoiceAssignment getAssignment() {
        return assignment;
    }

    public void setAssignment(MultipleChoiceAssignment assignment) {
        this.assignment = assignment;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }
}
