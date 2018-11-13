package com.netcracker.learnera.entity.template.lesson;

import com.netcracker.learnera.entity.media.File;
import com.netcracker.learnera.entity.template.Lesson;

import javax.persistence.*;

@Entity
@DiscriminatorValue("lecture")
public class Lecture extends Lesson {

    @Column(name = "lecture_text")
    private String lectureText;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "file_id")
    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getLectureText() {
        return lectureText;
    }

    public void setLectureText(String lectureText) {
        this.lectureText = lectureText;
    }
}
