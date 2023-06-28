package kz.kairatuly.finalproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private AuthorDto author;
}
