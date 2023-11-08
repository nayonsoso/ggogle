package com.example.ggoogle.service;

import com.example.ggoogle.entity.Post;
import com.example.ggoogle.model.PostDetailDto;
import com.example.ggoogle.model.PostForListDto;
import com.example.ggoogle.model.PostInputDto;

import java.util.List;

public interface PostService {
    public void createPost(Long siteUserId, PostInputDto postInputDto);
    public List<PostForListDto> getPostList();
    public PostDetailDto getPostDetail(Long postId);
}
