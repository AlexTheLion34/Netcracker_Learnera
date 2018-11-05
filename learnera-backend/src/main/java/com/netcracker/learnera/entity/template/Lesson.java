package com.netcracker.learnera.entity.template;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.netcracker.learnera.entity.IdentifiableEntity;
import com.netcracker.learnera.entity.media.File;
import com.netcracker.learnera.entity.template.lesson.LessonMessage;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Lesson implements IdentifiableEntity<Long> {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "week_id")
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    private Week week;

    @Column(name = "ordering")
    protected Integer ordering;

    @OneToMany(mappedBy = "destinationLesson", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public List<LessonMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<LessonMessage> messages) {
        this.messages = messages;
    }
}
