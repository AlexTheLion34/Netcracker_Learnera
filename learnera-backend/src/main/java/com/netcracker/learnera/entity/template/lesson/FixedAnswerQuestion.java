package com.netcracker.learnera.entity.template.lesson;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@DiscriminatorValue(value = "FIXED")
public class FixedAnswerQuestion extends Question {
    public FixedAnswerQuestion() {
    }
}
