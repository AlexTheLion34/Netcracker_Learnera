package com.netcracker.learnera.entity.template;

import com.fasterxml.jackson.annotation.*;
import com.netcracker.learnera.entity.IdentifiableEntity;
import com.netcracker.learnera.entity.template.lesson.Assignment;
import com.netcracker.learnera.entity.template.lesson.Lecture;
import com.netcracker.learnera.entity.template.lesson.LessonMessage;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Lecture.class, name = "lecture"),
        @JsonSubTypes.Type(value = Assignment.class, name = "assignment")
})
public abstract class Lesson implements IdentifiableEntity<Long> {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "module_id")
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    private Module module;

    @Column(name = "name")
    private String name;

    @Column(name = "ordering")
    protected Integer ordering;

    @Column(name = "type", insertable = false, updatable = false)
    protected String type;

    @OneToMany(mappedBy = "destinationLesson")
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    private List<LessonMessage> messages = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<LessonMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<LessonMessage> messages) {
        this.messages = messages;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
