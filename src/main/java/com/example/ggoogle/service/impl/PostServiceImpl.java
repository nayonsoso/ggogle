package com.example.ggoogle.service.impl;

import com.example.ggoogle.entity.Post;
import com.example.ggoogle.entity.SiteUser;
import com.example.ggoogle.model.*;
import com.example.ggoogle.repository.PostRepository;
import com.example.ggoogle.repository.SiteUserRepository;
import com.example.ggoogle.service.CommentService;
import com.example.ggoogle.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final SiteUserRepository siteUserRepository;

    @Override
    public void createPost(Long siteUserId, PostInputDto postInputDto) {
        //TODO: not found 에러 처리
        SiteUser siteUser = siteUserRepository.findById(siteUserId).orElse(null);

        Post post = Post.builder()
                .title(postInputDto.getTitle())
                .summary(postInputDto.getSummary())
                .link(postInputDto.getLink())
                .siteUser(siteUser)
                .build();

        postRepository.save(post);
    }

    @Override
    public List<PostForListDto> getPostList() {
        //TODO: 페이징 처리
        return postRepository.findAll().stream().map(Post::toPostForList)
                .collect(Collectors.toList());
    }

    @Override
    public PostDetailDto getPostDetail(Long postId) {
        //TODO: not found 애러 처리
        Post post = postRepository.findById(postId).orElse(null);

        return Post.toPostDetail(post);
    }
}
