package com.netcracker.learnera.entity.template.lesson;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(value = "MC")
public class MultipleChoiceQuestion extends Question {

    @OneToMany(mappedBy = "question", orphanRemoval = true, cascade = CascadeType.MERGE)
    private List<MultipleChoiceVariant> variants = new ArrayList<>();

    public MultipleChoiceQuestion() {
    }

    public List<MultipleChoiceVariant> getVariants() {
        return variants;
    }

    public void setVariants(List<MultipleChoiceVariant> variants) {
        this.variants = variants;
    }
}
