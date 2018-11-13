package com.netcracker.learnera.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.netcracker.learnera.entity.media.Image;
import com.netcracker.learnera.entity.template.CourseWeekDate;
import com.netcracker.learnera.entity.template.Template;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course implements IdentifiableEntity<Long> {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
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
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")}
    )
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    private List<Group> groups = new ArrayList<>();

    @OneToMany(mappedBy = "course", fetch=FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE,
            CascadeType.PERSIST},
            orphanRemoval = true)
    private List<CourseWeekDate> weekDates = new ArrayList<>();

    public Course() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getPassPercent() {
        return passPercent;
    }

    public void setPassPercent(Integer passPercent) {
        this.passPercent = passPercent;
    }

    public Integer getGoodPercent() {
        return goodPercent;
    }

    public void setGoodPercent(Integer goodPercent) {
        this.goodPercent = goodPercent;
    }

    public Integer getExcellentPercent() {
        return excellentPercent;
    }

    public void setExcellentPercent(Integer excellentPercent) {
        this.excellentPercent = excellentPercent;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<CourseWeekDate> getWeekDates() {
        return weekDates;
    }

    public void setWeekDates(List<CourseWeekDate> weekDates) {
        this.weekDates = weekDates;
    }

}
