package com.netcracker.learnera.entity.template.lesson;

import com.netcracker.learnera.entity.Course;
import com.netcracker.learnera.entity.User;
import com.netcracker.learnera.entity.template.Lesson;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_course_assignment_attempts")
public class AssignmentAttempt {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "answer")
    private String answer;

    @Column(name = "score")
    private Float score;

    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Assignment assignment;

}
