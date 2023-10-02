package com.example.ggoogle.service.impl;

import com.example.ggoogle.entity.Comment;
import com.example.ggoogle.entity.Post;
import com.example.ggoogle.entity.SiteUser;
import com.example.ggoogle.entity.UserProfile;
import com.example.ggoogle.model.PostDetailDto;
import com.example.ggoogle.model.PostForListDto;
import com.example.ggoogle.model.PostInputDto;
import com.example.ggoogle.repository.PostRepository;
import com.example.ggoogle.repository.SiteUserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.assertj.core.api.LocalTimeAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;

import java.util.*;

import static com.example.ggoogle.service.impl.ObjectForTest.*;
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

    @DisplayName("게시물 목록 조회 테스트 - 전체 게시물이 List<PostForListDto> 형식으로 리턴되는지")
    @Test
    void getPostListTest() {
        //given
        SiteUser siteUser = createSiteUser();
        Post post = createPost(siteUser);
        post.setCreatedTime(LocalDate.now().toString());
        post.setCommentCount(0);
        post.setLikeCount(0);

        ArrayList<Post> postList = new ArrayList<Post>();
        postList.add(post);
        postList.add(post);

        given(postRepository.findAll()).willReturn(postList);

        //when
        List<PostForListDto> postForListDtoList = postService.getPostList();

        //then
        PostForListDto postForListDto = postForListDtoList.get(0);
        assertThat(postForListDto.getTitle()).isEqualTo(post.getTitle());
        assertThat(postForListDto.getWriterName()).isEqualTo(siteUser.getName());
        assertThat(postForListDto.getPostedDate()).isEqualTo(post.getCreatedTime());
        assertThat(postForListDto.getLikeCount()).isEqualTo(post.getLikeCount());
        assertThat(postForListDto.getCommentCount()).isEqualTo(post.getCommentCount());

        assertThat(postForListDtoList.size()).isEqualTo(2);
    }

    @DisplayName("게시물 상세 조회 테스트 - 게시물 상세 정보가 PostDetailDto 형식으로 리턴되는지")
    @Test
    void getPostDetailTest() {
        //given
        SiteUser siteUser = createSiteUser();
        Post post = createPost(siteUser);
        post.setCreatedTime(LocalDate.now().toString());
        post.setCommentCount(0);
        post.setLikeCount(0);

        Comment c1 = createComment(post, siteUser);
        Comment c2 = createComment(post, siteUser);
        c1.setCreatedTime(LocalDateTime.now().toString());
        c2.setCreatedTime(LocalDateTime.now().toString());
        c1.addCommentTo(post);
        c2.addCommentTo(post);
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));

        //when
        PostDetailDto postDetailDto = postService.getPostDetail(1L);

        //then
        assertThat(postDetailDto).isExactlyInstanceOf(PostDetailDto.class);

        assertThat(postDetailDto.getWriterName()).isEqualTo(siteUser.getName());
        assertThat(postDetailDto.getPostedTime()).isEqualTo(post.getCreatedTime());
        assertThat(postDetailDto.getTitle()).isEqualTo(post.getTitle());
        assertThat(postDetailDto.getLink()).isEqualTo(post.getLink());
        assertThat(postDetailDto.getSummary()).isEqualTo(post.getSummary());
        assertThat(postDetailDto.getCommentCount()).isEqualTo(post.getCommentCount());
        assertThat(postDetailDto.getLikeCount()).isEqualTo(post.getLikeCount());

        assertThat(postDetailDto.getCommentList().size()).isEqualTo(2);
        assertThat(postDetailDto.getCommentList().get(0).getCommentedTime()).isEqualTo(c1.getCreatedTime());
        assertThat(postDetailDto.getCommentList().get(0).getContent()).isEqualTo(c1.getContent());
        assertThat(postDetailDto.getCommentList().get(0).getWriterName()).isEqualTo(c1.getSiteUser().getName());
    }

}