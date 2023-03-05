package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Set;

@Entity
@Table(name = "children")
@Getter
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "age", nullable = false)
    private int age;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Relative> relatives;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if(o == this) return true;
        if(!(o instanceof Child child)) return false;
        return id.equals(child.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}