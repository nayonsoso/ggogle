package com.example.ggoogle.service.impl;

import com.example.ggoogle.entity.Comment;
import com.example.ggoogle.entity.Post;
import com.example.ggoogle.entity.SiteUser;
import com.example.ggoogle.entity.UserProfile;
import com.example.ggoogle.model.PostInputDto;
import com.example.ggoogle.repository.PostRepository;
import com.example.ggoogle.repository.SiteUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @Mock
    private PostRepository postRepository;

    @Mock
    private SiteUserRepository siteUserRepository;

    @InjectMocks
    private PostServiceImpl postService;

    String testStr = "test";

    @DisplayName("게시물 저장 테스트 - 게시물 입력과 사용자 정보가 연결되어 save() 의 인자로 전달되는지")
    @Test
    void createPostTest() {
        //given
        SiteUser siteUser = createSiteUser();
        Post post = createPost(siteUser);
        given(siteUserRepository.findById(anyLong()))
                .willReturn(Optional.of(siteUser));
        PostInputDto postInputDto = PostInputDto.builder()
                        .title(post.getTitle())
                        .summary(post.getSummary())
                        .link(post.getLink())
                        .build();
        ArgumentCaptor<Post> argumentCaptor = ArgumentCaptor.forClass(Post.class);

        //when
        postService.createPost(anyLong(), postInputDto);

        //then
        then(postRepository)
                .should()
                .save(argumentCaptor.capture());

        Post beforeSavingPost = argumentCaptor.getValue();
        assertThat(beforeSavingPost.getTitle()).isEqualTo(postInputDto.getTitle());
        assertThat(beforeSavingPost.getLink()).isEqualTo(postInputDto.getLink());
        assertThat(beforeSavingPost.getSummary()).isEqualTo(postInputDto.getSummary());
        assertThat(beforeSavingPost.getSiteUser()).isEqualTo(siteUser);
        assertThat(beforeSavingPost.getCommentList().size()).isEqualTo(0);
    }

    @Test
    void getPostList() {
    }

    @Test
    void getPostDetail() {
    }



    Post createPost(SiteUser siteUser){
        return Post.builder()
                .title(testStr+" title")
                .link(testStr+" link")
                .summary(testStr+" summary")
                .siteUser(siteUser)
                .build();
    }

    Comment createComment(Post post, SiteUser siteUser){
        return Comment.builder()
                .post(post)
                .content(testStr+" content")
                .siteUser(siteUser)
                .build();
    }

    SiteUser createSiteUser(){
        return SiteUser.builder()
                .userId(testStr + "userId")
                .name(testStr + "name")
                .password(testStr + "password")
                .build();
    }
}