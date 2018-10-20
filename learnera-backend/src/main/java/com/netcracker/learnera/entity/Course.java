package com.netcracker.learnera.entity;


import com.netcracker.learnera.entity.media.Image;
import com.netcracker.learnera.entity.template.CourseWeekDate;
import com.netcracker.learnera.entity.template.Template;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private Template template;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "avatar_id")
    private Image avatar;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "pass_percent")
    private Integer passPercent;

    @Column(name = "good_percent")
    private Integer goodPercent;

    @Column(name = "excellent_percent")
    private Integer excellentPercent;

    @ManyToMany
    @JoinTable(
            name = "course_groups",
            joinColumns = { @JoinColumn(name = "course_id") },
            inverseJoinColumns = { @JoinColumn(name = "group_id") }
    )
    private List<Group> groups = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseWeekDate> weekDates = new ArrayList<>();

}
