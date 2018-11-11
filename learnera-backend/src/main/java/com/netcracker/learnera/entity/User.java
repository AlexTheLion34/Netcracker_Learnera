package com.netcracker.learnera.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.netcracker.learnera.entity.enums.UserRole;
import com.netcracker.learnera.entity.template.Template;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements IdentifiableEntity<Long> {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password_hash")
    private String password;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserInfo info;

    @OneToMany(mappedBy = "curator", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Group> curatedGroups = new ArrayList<>();

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Template> templates = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(
            name = "group_users",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")}
    )
    private List<Group> studyGroups = new ArrayList<>();

    public User() {

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }

    public List<Group> getCuratedGroups() {
        return curatedGroups;
    }

    public void setCuratedGroups(List<Group> curatedGroups) {
        this.curatedGroups = curatedGroups;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

    public List<Group> getStudyGroups() {
        return studyGroups;
    }

    public void setStudyGroups(List<Group> studyGroups) {
        this.studyGroups = studyGroups;
    }
}
