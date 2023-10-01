package com.example.ggoogle.repository;

import com.example.ggoogle.entity.Comment;
import com.example.ggoogle.entity.Likes;
import com.example.ggoogle.entity.Post;
import com.example.ggoogle.entity.SiteUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CRUDTest {
    @Autowired
    private SiteUserRepository siteUserRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private PostRepository postRepository;

    String testStr = "test";

    @DisplayName("PostRepository.save 테스트 - 설정한 디폴트값이나 형식이 잘 들어갔는지")
    @Test
    void PostRepository_save_Test(){
        //TODO: TestEntityManager나 persist 등을 이용하면 더 좋은 테스트 코드를 짤 수 있을 것 같음
        //given
        SiteUser user = createSiteUser();
        Post post = createPost(user);
        siteUserRepository.save(user);

        //when
        Post savedPost = postRepository.save(post);

        //then
        assertThat(savedPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(savedPost.getLink()).isEqualTo(post.getLink());
        assertThat(savedPost.getSummary()).isEqualTo(post.getSummary());
        assertThat(savedPost.getSiteUser()).isEqualTo(user);
        assertThat(savedPost.getCommentList().size()).isEqualTo(0);

        assertThat(postRepository.findAll().size()).isGreaterThan(0);
        assertThat(savedPost.getCreatedTime().split(" ").length).isEqualTo(2);
        assertThat(savedPost.getCommentCount()).isEqualTo(0);
        assertThat(savedPost.getLikeCount()).isEqualTo(0);
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

    Likes createLikes(SiteUser siteUser, Post post){
        return Likes.builder()
                .siteUser(siteUser)
                .post(post)
                .build();
    }
}
