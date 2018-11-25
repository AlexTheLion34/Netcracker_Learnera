package com.netcracker.learnera.entity.template;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CourseModuleDateId implements Serializable {

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "module_id")
    private Long moduleId;

    public CourseModuleDateId() {
    }

    public CourseModuleDateId(Long courseId, Long moduleId) {
        this.courseId = courseId;
        this.moduleId = moduleId;
    }


    @Override
    public int hashCode() {
        return Objects.hash(courseId, moduleId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        CourseModuleDateId that = (CourseModuleDateId) o;
        return Objects.equals(courseId, that.courseId) &&
                Objects.equals(moduleId, that.moduleId);
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }
}
