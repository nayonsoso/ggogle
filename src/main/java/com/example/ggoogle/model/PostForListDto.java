package com.example.ggoogle.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostForListDto {
    private String title;
    private String writerName;
    private String postedDate;
    private int likeCount;
    private int commentCount;
}
