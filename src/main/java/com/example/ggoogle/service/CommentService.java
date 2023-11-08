package com.example.ggoogle.service;

import com.example.ggoogle.model.CommentForListDto;
import com.example.ggoogle.model.CommentInputDto;

import java.util.List;

public interface CommentService {
    public void createComment(Long siteUserId, Long postId, CommentInputDto commentInputDto);
}
