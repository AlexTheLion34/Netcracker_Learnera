package com.netcracker.learnera.entity.template;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CourseWeekDateId implements Serializable {

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "week_id")
    private Long weekId;

    public CourseWeekDateId() {
    }


    @Override
    public int hashCode() {
        return Objects.hash(courseId, weekId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        CourseWeekDateId that = (CourseWeekDateId) o;
        return Objects.equals(courseId, that.courseId) &&
                Objects.equals(weekId, that.weekId);
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getWeekId() {
        return weekId;
    }

    public void setWeekId(Long weekId) {
        this.weekId = weekId;
    }
}
