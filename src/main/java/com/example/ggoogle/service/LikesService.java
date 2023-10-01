package com.example.ggoogle.service;

import com.example.ggoogle.model.PostForListDto;

import java.util.List;

public interface LikesService {
    public void createLikes(Long siteUserId, Long postId);
    public List<PostForListDto> getPostLikedBy(Long siteUserId);
    public List<String> getUserLikes(Long postId);
}
