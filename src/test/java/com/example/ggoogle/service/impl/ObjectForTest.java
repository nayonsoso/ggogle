package com.example.ggoogle.service.impl;

import com.example.ggoogle.entity.Comment;
import com.example.ggoogle.entity.Post;
import com.example.ggoogle.entity.SiteUser;

public class ObjectForTest {
  private static final String testStr = "test";

  public static Post createPost(SiteUser siteUser){
    return Post.builder()
        .title(testStr+" title")
        .link(testStr+" link")
        .summary(testStr+" summary")
        .siteUser(siteUser)
        .build();
  }

  public static Comment createComment(Post post, SiteUser siteUser){
    return Comment.builder()
        .post(post)
        .content(testStr+" content")
        .siteUser(siteUser)
        .build();
  }

  public static SiteUser createSiteUser(){
    return SiteUser.builder()
        .userId(testStr + " userId")
        .name(testStr + " name")
        .password(testStr + " password")
        .build();
  }
}
