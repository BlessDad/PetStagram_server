package com.example.myspringserver.repository;

import com.example.myspringserver.entity.Walking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WalkingRepository extends JpaRepository<Walking, Integer> {
    @Query("SELECT w FROM Walking w WHERE w.walking_start BETWEEN :startDateTime AND :endDateTime")
    List<Walking> findByWalking_startBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
