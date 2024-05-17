package com.example.myspringserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer comment_id;

    @Column (name = "comment_writer")
    private String comment_writer;

    @Column (name = "comment_content")
    private String comment_content;
}
