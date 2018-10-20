package com.netcracker.learnera.entity.template;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "LECTURE")
public class Lecture extends Lesson {
    public Lecture() {
    }
}
