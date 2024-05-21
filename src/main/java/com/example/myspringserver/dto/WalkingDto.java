package com.example.myspringserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class WalkingDto {
    private Integer walking_id;
    private LocalDateTime walking_start;
    private LocalDateTime walking_end;
    private Double walking_distance;
    private Integer walking_calorie;
    private Double walking_speed;

    private Integer user_id;
}
