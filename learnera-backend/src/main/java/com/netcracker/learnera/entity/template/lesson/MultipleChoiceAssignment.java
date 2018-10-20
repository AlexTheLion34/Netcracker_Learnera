package com.netcracker.learnera.entity.template.lesson;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("MC")
public class MultipleChoiceAssignment extends Assignment {

    @OneToMany(mappedBy = "assignment", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<MultipleChoiceVariant> variants = new ArrayList<>();

    public MultipleChoiceAssignment() {
    }

    public List<MultipleChoiceVariant> getVariants() {
        return variants;
    }

    public void setVariants(List<MultipleChoiceVariant> variants) {
        this.variants = variants;
    }
}
