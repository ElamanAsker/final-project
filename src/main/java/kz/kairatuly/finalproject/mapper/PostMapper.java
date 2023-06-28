package kz.kairatuly.finalproject.mapper;

import kz.kairatuly.finalproject.dto.PostDto;
import kz.kairatuly.finalproject.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(source = "content", target = "content")
    PostDto toDto(Post post);

    @Mapping(source = "content", target = "content")
    Post toEntity(PostDto postDto);

    List<PostDto> toDtoList(List<Post> posts);
}
