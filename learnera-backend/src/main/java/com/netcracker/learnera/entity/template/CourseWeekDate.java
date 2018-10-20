package com.netcracker.learnera.entity.template;

import com.netcracker.learnera.entity.Course;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "course_week_dates")
public class CourseWeekDate {

    @EmbeddedId
    private CourseWeekDateId id;

    @ManyToOne
    @MapsId(value = "course_id")
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @MapsId(value = "week_id")
    @JoinColumn(name = "week_id")
    private Week week;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

}
