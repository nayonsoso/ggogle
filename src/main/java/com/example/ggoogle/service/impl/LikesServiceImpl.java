package com.example.ggoogle.service.impl;


import com.example.ggoogle.entity.Likes;
import com.example.ggoogle.entity.Post;
import com.example.ggoogle.entity.SiteUser;
import com.example.ggoogle.model.CommentInputDto;
import com.example.ggoogle.model.PostForListDto;
import com.example.ggoogle.repository.LikesRepository;
import com.example.ggoogle.repository.PostRepository;
import com.example.ggoogle.repository.SiteUserRepository;
import com.example.ggoogle.service.CommentService;
import com.example.ggoogle.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LikesServiceImpl implements LikesService {
    private final LikesRepository likesRepository;
    private final SiteUserRepository siteUserRepository;
    private final PostRepository postRepository;

    @Override
    public void createLikes(Long siteUserId, Long postId) {
        //TODO: not found 에러처리
        SiteUser siteUser = siteUserRepository.findById(siteUserId).orElse(null);
        Post post = postRepository.findById(postId).orElse(null);

        Likes likes = Likes.builder()
                .post(post)
                .siteUser(siteUser)
                .build();

        likesRepository.save(likes);
    }

    @Override
    public List<PostForListDto> getPostLikedBy(Long siteUserId) {
        //TODO: not found 에러처리
        SiteUser siteUser = siteUserRepository.findById(siteUserId).orElse(null);

        List<Likes> likes = likesRepository.findAllBySiteUser(siteUser);
        List<Post> posts = likes.stream().map(Likes::getPost).toList();
        return posts.stream().map(Post::toPostForList).collect(Collectors.toList());
    }

    @Override
    public List<String> getUserLikes(Long postId) {
        //TODO: not found 에러처리
        Post post = postRepository.findById(postId).orElse(null);

        List<Likes> likes = likesRepository.findAllByPost(post);
        List<SiteUser> siteUsers = likes.stream().map(Likes::getSiteUser).toList();
        return siteUsers.stream().map(SiteUser::getName).collect(Collectors.toList());
    }
}
