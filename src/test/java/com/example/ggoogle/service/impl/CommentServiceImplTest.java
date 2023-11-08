package com.example.ggoogle.service.impl;

import static com.example.ggoogle.service.impl.ObjectForTest.createPost;
import static com.example.ggoogle.service.impl.ObjectForTest.createSiteUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.example.ggoogle.entity.Comment;
import com.example.ggoogle.entity.Post;
import com.example.ggoogle.entity.SiteUser;
import com.example.ggoogle.model.CommentInputDto;
import com.example.ggoogle.repository.CommentRepository;
import com.example.ggoogle.repository.PostRepository;
import com.example.ggoogle.repository.SiteUserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
  @Mock
  private SiteUserRepository siteUserRepository;

  @Mock
  private CommentRepository commentRepository;

  @Mock
  private PostRepository postRepository;

  @InjectMocks
  private CommentServiceImpl commentService;

  @DisplayName("댓글 작성 테스트 - 유저와 포스트가 함께 저장되는지, 게시글과의 양방향 매핑이 잘 되었는지 테스트")
  @Test
  void createCommentTest() {
    //given
    SiteUser siteUser = createSiteUser();
    Post post = createPost(siteUser);
    siteUser.setId(1L);
    post.setId(1L);
    given(siteUserRepository.findById(anyLong())).willReturn(Optional.of(siteUser));
    given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
    CommentInputDto commentInputDto = CommentInputDto.builder().content("content test").build();
    ArgumentCaptor<Comment> argumentCaptor = ArgumentCaptor.forClass(Comment.class);

    //when
    commentService.createComment(siteUser.getId(), post.getId(), commentInputDto);

    //then
    then(commentRepository)
        .should()
        .save(argumentCaptor.capture());
    Comment beforeSavingComment = argumentCaptor.getValue();

    // post 와 user 이 알맞게 저장되는지
    assertThat(beforeSavingComment.getPost()).isEqualTo(post);
    assertThat(beforeSavingComment.getSiteUser()).isEqualTo(siteUser);
    assertThat(beforeSavingComment.getContent()).isEqualTo(commentInputDto.getContent());

    // 연관관계 편의 메서드가 잘 작동하는지
    assertThat(beforeSavingComment.getPost().getCommentList().contains(beforeSavingComment)).isTrue();
  }
}