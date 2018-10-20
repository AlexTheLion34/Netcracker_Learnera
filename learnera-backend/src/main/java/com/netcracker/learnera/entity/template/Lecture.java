package com.netcracker.learnera.entity.template;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@DiscriminatorValue(value = "LECTURE")
public class Lecture extends Lesson {
}
