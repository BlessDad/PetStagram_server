package com.example.myspringserver.repository;

import com.example.myspringserver.entity.Walking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalkingRepository extends JpaRepository<Walking, Integer> {
}
