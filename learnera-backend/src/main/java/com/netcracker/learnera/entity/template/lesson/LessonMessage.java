package com.netcracker.learnera.entity.template.lesson;

import com.netcracker.learnera.entity.User;
import com.netcracker.learnera.entity.template.Lesson;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "lesson_messages")
public class LessonMessage {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson destinationLesson;

    @Column(name = "message")
    private String message;

    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

}
