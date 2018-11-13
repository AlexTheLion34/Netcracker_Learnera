package com.netcracker.learnera.entity.template;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.netcracker.learnera.entity.IdentifiableEntity;
import com.netcracker.learnera.entity.User;
import com.netcracker.learnera.entity.media.Image;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "templates")
public class Template implements IdentifiableEntity<Long> {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    private User teacher;

    @Column(name = "name")
    private String name;

    @Column(name = "is_completed")
    private boolean isCompleted;

    @Column(name = "description")
    private String description;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_id")
    private Image avatar;

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("week_number asc")
    private List<Week> weeks = new ArrayList<>();

    public Template() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public List<Week> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<Week> weeks) {
        this.weeks = weeks;
    }
}
