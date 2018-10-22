package com.netcracker.learnera.entity.template.lesson;

import javax.persistence.*;

@Entity
@Table(name = "mc_question_variants")
public class MultipleChoiceVariant {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id")
    private MultipleChoiceQuestion question;

    @Column(name = "choice_text")
    private String choiceText;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    public MultipleChoiceVariant() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public MultipleChoiceQuestion getQuestion() {
        return question;
    }

    public void setQuestion(MultipleChoiceQuestion question) {
        this.question = question;
    }
}
