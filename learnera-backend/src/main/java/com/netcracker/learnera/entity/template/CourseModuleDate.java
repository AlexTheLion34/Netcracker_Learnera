package com.netcracker.learnera.entity.template;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.netcracker.learnera.entity.Course;
import com.netcracker.learnera.entity.IdentifiableEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "course_module_dates")
public class CourseModuleDate implements IdentifiableEntity<CourseModuleDateId> {

    @EmbeddedId
    private CourseModuleDateId id;

    @ManyToOne
    @MapsId(value = "course_id")
    @JoinColumn(name = "course_id")
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    private Course course;

    @ManyToOne
    @MapsId(value = "module_id")
    @JoinColumn(name = "module_id")
    private Module module;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    public CourseModuleDate() {
    }

    public CourseModuleDateId getId() {
        return id;
    }

    public void setId(CourseModuleDateId id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
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
