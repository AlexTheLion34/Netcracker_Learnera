package com.netcracker.learnera.entity.template.lesson;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FIXED")
public class FixedAnswerAssignment extends Assignment {
}
