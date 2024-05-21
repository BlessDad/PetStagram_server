package com.example.myspringserver.entity;

import jakarta.persistence.*;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "walking")
@Entity
public class Walking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "walking_id")
    private Integer walking_id;

    @Column (name = "walking_start")
    private LocalDateTime walking_start;

    @Column (name = "walking_end")
    private LocalDateTime walking_end;

    @Column (name = "walking_distance")
    private Double walking_distance;

    @Column (name = "walking_calorie")
    private Integer walking_calorie;

    @Column (name = "walking_speed")
    private Double walking_speed;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
