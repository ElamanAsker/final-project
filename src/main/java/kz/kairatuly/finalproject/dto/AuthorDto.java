package kz.kairatuly.finalproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthorDto {

    private Long id;
    private String fullName;
    private String email;
}
