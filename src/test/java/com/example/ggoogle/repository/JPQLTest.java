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
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JPQLTest {
    @Autowired
    private SiteUserRepository siteUserRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private PostRepository postRepository;

    String testStr = "test";

    @DisplayName("CommentRepository.findAllByPost 테스트")
    @Test
    void CommentRepository_findAllByPost_Test(){
        //TODO: TestEntityManager나 persist 등을 이용하면 더 좋은 테스트 코드를 짤 수 있을 것 같음
        //given
        SiteUser user = createSiteUser();
        Post post1 = createPost(user);
        Post post2 = createPost(user);
        Comment comment1 = createComment(post1, user);
        Comment comment2 = createComment(post2, user);
        Comment comment3 = createComment(post2, user);

        siteUserRepository.save(user);
        postRepository.save(post1);
        postRepository.save(post2);
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        //when
        List<Comment> commentList = commentRepository.findAllByPost(post2);

        //then
        assertThat(commentList.contains(comment2) && commentList.contains(comment3)).isTrue();
        assertThat(commentList.contains(comment1)).isFalse();
    }

    @DisplayName("LikesRepository.findAllBySiteUser 테스트")
    @Test
    void LikesRepository_findAllBySiteUser_Test(){
        //given
        SiteUser user1 = createSiteUser();
        SiteUser user2 = createSiteUser();
        Post post1 = createPost(user1);
        Post post2 = createPost(user2);
        siteUserRepository.save(user1);
        siteUserRepository.save(user2);
        postRepository.save(post1);
        postRepository.save(post2);

        Likes likes1 = createLikes(user1, post1);
        Likes likes2 = createLikes(user2, post2);
        likesRepository.save(likes1);
        likesRepository.save(likes2);

        //when
        List<Likes> likesList = likesRepository.findAllBySiteUser(user1);

        //then
        assertThat(likesList.contains(likes1)).isTrue();
        assertThat(likesList.contains(likes2)).isFalse();
    }

    @DisplayName("LikesRepository.findAllByPost 테스트")
    @Test
    void LikesRepository_findAllByPost_Test(){
        //given
        SiteUser user1 = createSiteUser();
        SiteUser user2 = createSiteUser();
        Post post1 = createPost(user1);
        Post post2 = createPost(user2);
        siteUserRepository.save(user1);
        siteUserRepository.save(user2);
        postRepository.save(post1);
        postRepository.save(post2);

        Likes likes1 = createLikes(user1, post1);
        Likes likes2 = createLikes(user2, post2);
        likesRepository.save(likes1);
        likesRepository.save(likes2);

        //when
        List<Likes> likesList = likesRepository.findAllByPost(post1);

        //then
        assertThat(likesList.contains(likes1)).isTrue();
        assertThat(likesList.contains(likes2)).isFalse();
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
