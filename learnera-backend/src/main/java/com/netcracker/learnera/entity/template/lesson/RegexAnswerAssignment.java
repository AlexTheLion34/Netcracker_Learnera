package com.netcracker.learnera.entity.template.lesson;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("REGEX")
public class RegexAnswerAssignment extends Assignment {
    public RegexAnswerAssignment() {
    }
}
