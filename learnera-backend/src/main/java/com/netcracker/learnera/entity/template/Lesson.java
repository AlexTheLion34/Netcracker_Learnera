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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public String getLessonText() {
        return lessonText;
    }

    public void setLessonText(String lessonText) {
        this.lessonText = lessonText;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public List<LessonMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<LessonMessage> messages) {
        this.messages = messages;
    }
}
