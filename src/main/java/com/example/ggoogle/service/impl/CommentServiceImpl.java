package com.example.ggoogle.service.impl;


import com.example.ggoogle.entity.Comment;
import com.example.ggoogle.entity.Post;
import com.example.ggoogle.entity.SiteUser;
import com.example.ggoogle.model.CommentForListDto;
import com.example.ggoogle.model.CommentInputDto;
import com.example.ggoogle.repository.CommentRepository;
import com.example.ggoogle.repository.PostRepository;
import com.example.ggoogle.repository.SiteUserRepository;
import com.example.ggoogle.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final SiteUserRepository siteUserRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public void createComment(Long siteUserId, Long postId, CommentInputDto commentInputDto) {
        //TODO: not found 에러처리
        SiteUser siteUser = siteUserRepository.findById(siteUserId).orElse(null);
        Post post = postRepository.findById(postId).orElse(null);

        Comment comment = Comment.builder()
                .post(post)
                .siteUser(siteUser)
                .content(commentInputDto.getContent())
                .build();

        comment.addCommentTo(post);
        commentRepository.save(comment);
    }
}
