package com.example.ggoogle.service.impl;

import static com.example.ggoogle.service.impl.ObjectForTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.example.ggoogle.entity.Likes;
import com.example.ggoogle.entity.Post;
import com.example.ggoogle.entity.SiteUser;
import com.example.ggoogle.model.PostForListDto;
import com.example.ggoogle.repository.CommentRepository;
import com.example.ggoogle.repository.LikesRepository;
import com.example.ggoogle.repository.PostRepository;
import com.example.ggoogle.repository.SiteUserRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LikesServiceImplTest {
  @Mock
  private SiteUserRepository siteUserRepository;
  @Mock
  private LikesRepository likesRepository;
  @Mock
  private PostRepository postRepository;
  @InjectMocks
  LikesServiceImpl likesService;

  @DisplayName("좋아요 생성 테스트 - 유저와 게시물이 매핑되어 저장되는지")
  @Test
  void createLikesTest() {
    //given
    SiteUser siteUser = createSiteUser();
    Post post = createPost(siteUser);
    siteUser.setId(1L);
    post.setId(1L);
    given(siteUserRepository.findById(anyLong())).willReturn(Optional.ofNullable(siteUser));
    given(postRepository.findById(anyLong())).willReturn(Optional.ofNullable(post));
    ArgumentCaptor<Likes> argumentCaptor = ArgumentCaptor.forClass(Likes.class);

    //when
    likesService.createLikes(siteUser.getId(), post.getId());

    //then
    then(likesRepository)
        .should()
        .save(argumentCaptor.capture());
    Likes beforeSaveLikes = argumentCaptor.getValue();
    assertThat(beforeSaveLikes.getPost()).isEqualTo(post);
    assertThat(beforeSaveLikes.getSiteUser()).isEqualTo(siteUser);
  }

  @DisplayName("사용자가 좋아요한 게시물 조회 테스트")
  @Test
  void getPostLikedByTest() {
    //given - 한명의 유저가 두개의 게시물에 좋아요를 누른 상황
    SiteUser siteUser = createSiteUser();
    Post post1 = createPost(siteUser);
    Post post2 = createPost(siteUser);
    siteUser.setId(1L);
    post1.setCreatedTime(LocalDate.now().toString());
    post2.setCreatedTime(LocalDate.now().toString());
    post1.setCommentCount(1);
    post1.setLikeCount(1);
    post2.setCommentCount(2);
    post2.setLikeCount(2);
    Likes likes1 = createLikes(siteUser, post1);
    Likes likes2 = createLikes(siteUser, post2);
    List<Likes> likesList = new ArrayList<>();
    likesList.add(likes1);
    likesList.add(likes2);

    given(siteUserRepository.findById(anyLong())).willReturn(Optional.of(siteUser));
    given(likesRepository.findAllBySiteUser(any(SiteUser.class))).willReturn(likesList);

    //when
    List<PostForListDto> postLikedByUser = likesService.getPostLikedBy(siteUser.getId());

    //then
    assertThat(postLikedByUser.size()).isEqualTo(2);
    List<String> writerNames = postLikedByUser.stream().map(PostForListDto::getWriterName).toList();
    assertThat(writerNames.get(0)).isEqualTo(siteUser.getName());
    assertThat(writerNames.get(1)).isEqualTo(siteUser.getName());
  }

  @DisplayName("게시물을 좋아요한 사용자이름 조회 테스트")
  @Test
  void getUserLikesTest() {
    //given - 두명의 유저가 하나의 게시물에 좋아요를 누른 상황
    SiteUser siteUser1 = createSiteUser();
    SiteUser siteUser2 = createSiteUser();
    Post post = createPost(siteUser1);
    siteUser1.setName("1");
    siteUser2.setName("2");
    post.setId(1L);
    Likes likes1 = createLikes(siteUser1, post);
    Likes likes2 = createLikes(siteUser2, post);
    List<Likes> likesList = new ArrayList<>();
    likesList.add(likes1);
    likesList.add(likes2);

    given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
    given(likesRepository.findAllByPost(any(Post.class))).willReturn(likesList);

    //when
    List<String> userNameWhoLikedPost = likesService.getUserLikes(post.getId());

    //then
    assertThat(userNameWhoLikedPost.size()).isEqualTo(2);
    assertThat(userNameWhoLikedPost.contains(siteUser1.getName())).isTrue();
    assertThat(userNameWhoLikedPost.contains(siteUser2.getName())).isTrue();
  }
}