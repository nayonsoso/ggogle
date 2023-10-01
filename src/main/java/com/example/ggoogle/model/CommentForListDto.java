package com.example.ggoogle.model;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentForListDto {
    private String writerName;
    private String commentedTime;
    private String content;
}
