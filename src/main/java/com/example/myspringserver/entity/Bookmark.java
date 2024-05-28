package com.example.myspringserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "bookmark")

public class Bookmark {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer bookmark_id;

    @Column (name = "bookmark_address")
    private String bookmark_address;

    @Column (name = "bookmark_name")
    private String bookmark_name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
