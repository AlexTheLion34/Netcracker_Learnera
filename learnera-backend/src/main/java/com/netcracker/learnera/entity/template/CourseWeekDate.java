package com.netcracker.learnera.entity.template;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.netcracker.learnera.entity.Course;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "course_week_dates")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
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

    public CourseWeekDate() {
    }

    public CourseWeekDateId getId() {
        return id;
    }

    public void setId(CourseWeekDateId id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
