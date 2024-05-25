package com.example.myspringserver.repository;
import com.example.myspringserver.entity.Post;
import com.example.myspringserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostById(Integer id);

    @Query(value = "SELECT u.user_nickname FROM posts p INNER JOIN user u ON p.user_id = u.user_id WHERE p.user_id = :user_id",
    nativeQuery = true)
    String findUserNicknameByUserId(@Param("user_id") Integer user_id);
}
