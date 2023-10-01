package com.example.ggoogle.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

import com.example.ggoogle.entity.Comment;
import com.example.ggoogle.entity.Post;
import com.example.ggoogle.entity.SiteUser;
import com.example.ggoogle.entity.UserProfile;
import java.util.ArrayList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

/* H2 설정
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
*/

@TestPropertySource(locations = "classpath:application-db.yml")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RelationshipTest {
  @Autowired
  private SiteUserRepository siteUserRepository;

  @Autowired
  private UserProfileRepository userProfileRepository;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private CommentRepository commentRepository;

  String testStr = "test";

  @DisplayName("일대일 단방향 조회 테스트")
  @Test
  void oneToOne_OneWay_find_Test(){
    // given
    SiteUser savedSiteUser = siteUserRepository.save(createSiteUser());
    UserProfile savedProfile = userProfileRepository.save(createUserProfile(savedSiteUser));

    // when
    UserProfile foundProfile = userProfileRepository.findById(savedProfile.getId())
        .orElseThrow(IllegalArgumentException::new);

    // then
    assertThat(foundProfile.getBlog()).isEqualTo(testStr+" blog");
    assertThat(foundProfile.getGithub()).isEqualTo(testStr+" github");
    assertThat(foundProfile.getLinkedIn()).isEqualTo(testStr+" linkedIn");
  }

  @DisplayName("1:1 양방향에서 orphanRemoval 작동함")
  @Test
  void UserProfile_OrphanRemoval_Test(){
    //given
    SiteUser savedSiteUser = siteUserRepository.save(createSiteUser());
    UserProfile savedProfile = userProfileRepository.save(createUserProfile(savedSiteUser));
    savedSiteUser.editProfile(savedProfile);

    int before_user = siteUserRepository.findAll().size();
    int before_profile = userProfileRepository.findAll().size();

    //when
    siteUserRepository.delete(savedSiteUser);

    //then
    assertThat(siteUserRepository.findAll().size()).isEqualTo(before_user - 1);
    assertThat(userProfileRepository.findAll().size()).isEqualTo(before_profile - 1);
  }

  @DisplayName("1:N 양방향에서 orphanRemoval 작동함")
  @Test
  void Comment_OrphanRemoval_Test(){
    //given
    SiteUser savedSiteUser = siteUserRepository.save(createSiteUser());
    Post savedPost = postRepository.save(createPost());
    Comment savedComment = commentRepository.save(createComment(savedPost, savedSiteUser));
    savedComment.addCommentTo(savedPost);
    int before_post = postRepository.findAll().size();
    int before_comment = commentRepository.findAll().size();

    // when
    postRepository.delete(savedPost);

    assertThat(postRepository.findAll().size()).isEqualTo(before_post - 1);
    assertThat(commentRepository.findAll().size()).isEqualTo(before_comment - 1);
  }



  SiteUser createSiteUser(){
    return SiteUser.builder()
        .userId(testStr + "userId")
        .name(testStr + "name")
        .password(testStr + "password")
        .build();
  }

  UserProfile createUserProfile(SiteUser siteUser){
    return UserProfile.builder()
        .blog(testStr+" blog")
        .github(testStr+" github")
        .linkedIn(testStr+" linkedIn")
        .siteUser(siteUser)
        .build();
  }

  Post createPost(){
    return Post.builder()
        .title(testStr+" title")
        .link(testStr+" link")
        .summary(testStr+" summary")
        .build();
  }

  Comment createComment(Post post, SiteUser siteUser){
    return Comment.builder()
        .post(post)
        .content(testStr+" content")
        .siteUser(siteUser)
        .build();
  }
}
