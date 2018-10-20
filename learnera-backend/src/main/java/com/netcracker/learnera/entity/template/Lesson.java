package com.netcracker.learnera.entity.template;

import com.netcracker.learnera.entity.media.File;
import com.netcracker.learnera.entity.template.lesson.LessonMessage;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Lesson {

    @Id
    @GeneratedValue
    @Column(name = "id")
    protected Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "week_id")
    protected Week week;

    @Column(name = "lesson_text")
    protected String lessonText;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "file_id")
    protected File file;

    @OneToMany(mappedBy = "destinationLesson", cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<LessonMessage> messages = new ArrayList<>();

}
