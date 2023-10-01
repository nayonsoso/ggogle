package com.example.ggoogle.model;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDetailDto {
    private String writerName;
    private String postedTime;
    private String title;
    private String link;
    private String summary;
    private int commentCount;
    private int likeCount;
    private List<CommentForListDto> commentList;
}