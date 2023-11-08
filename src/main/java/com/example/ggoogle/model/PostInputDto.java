package com.example.ggoogle.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostInputDto {
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "링크를 입력해주세요.")
    private String link;
    @NotBlank(message = "요약을 입력해주세요.")
    private String summary;
}