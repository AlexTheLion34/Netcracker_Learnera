package com.netcracker.learnera.entity.template;

import javax.persistence.*;

@Entity
@Table(name = "weeks")
public class Week {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "template_id")
    private Template template;

    @Column(name = "week_number")
    private Integer weekNumber;

    @Column(name = "name")
    private String name;

}
