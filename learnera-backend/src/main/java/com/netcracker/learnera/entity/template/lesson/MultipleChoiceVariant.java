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

}
