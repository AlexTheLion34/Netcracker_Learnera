package com.netcracker.learnera.entity.template.lesson;

import com.netcracker.learnera.entity.media.File;
import com.netcracker.learnera.entity.template.Lesson;

import javax.persistence.*;

@Entity
@Table(name = "lectures")
@PrimaryKeyJoinColumn(name = "lesson_id")
public class Lecture extends Lesson {

    @Column(name = "lecture_text")
    private String lectureText;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "file_id")
    private File file;

    public Lecture() {
    }
}
