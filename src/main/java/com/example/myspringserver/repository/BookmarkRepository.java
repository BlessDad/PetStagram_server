package com.example.myspringserver.repository;

import com.example.myspringserver.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {
    @Query(value = "SELECT * FROM Bookmark b WHERE b.user_id = :user_id", nativeQuery = true)
    List<Bookmark> findByUser_user_id(Integer user_id);
}
