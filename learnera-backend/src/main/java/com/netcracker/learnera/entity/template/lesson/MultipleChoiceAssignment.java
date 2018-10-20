package com.netcracker.learnera.entity.template.lesson;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("MC")
public class MultipleChoiceAssignment extends Assignment {

    @OneToMany(mappedBy = "assignment", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<MultipleChoiceVariant> variants = new ArrayList<>();

}
