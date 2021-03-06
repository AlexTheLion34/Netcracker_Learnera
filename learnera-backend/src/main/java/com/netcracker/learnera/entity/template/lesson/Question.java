package com.netcracker.learnera.entity.template.lesson;

import com.fasterxml.jackson.annotation.*;
import com.netcracker.learnera.entity.IdentifiableEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = FixedAnswerQuestion.class, name = "fixed"),
        @JsonSubTypes.Type(value = MultipleChoiceQuestion.class, name = "mc"),
        @JsonSubTypes.Type(value = RegexAnswerQuestion.class, name="regex")
})
public abstract class Question implements IdentifiableEntity<Long> {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    private Assignment assignment;

    @Column(name = "ordering")
    private Integer ordering;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "answer")
    private String answer;

    @Column(name = "points")
    private Float points;

    @OneToMany(mappedBy = "question")
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    private List<QuestionAttempt> attempts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

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

    public List<QuestionAttempt> getAttempts() {
        return attempts;
    }

    public void setAttempts(List<QuestionAttempt> attempts) {
        this.attempts = attempts;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}
