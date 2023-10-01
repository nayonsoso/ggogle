package com.example.ggoogle.repository;

import com.example.ggoogle.entity.Comment;
import com.example.ggoogle.entity.Post;
import com.example.ggoogle.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
}
