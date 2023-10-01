package com.example.ggoogle.repository;

import com.example.ggoogle.entity.Likes;
import com.example.ggoogle.entity.Post;
import com.example.ggoogle.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    List<Likes> findAllBySiteUser(SiteUser siteUser);
    List<Likes> findAllByPost(Post post);
}
