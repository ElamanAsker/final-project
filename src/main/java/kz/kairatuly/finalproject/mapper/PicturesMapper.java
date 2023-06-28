package kz.kairatuly.finalproject.mapper;

import kz.kairatuly.finalproject.dto.PicturesDto;
import kz.kairatuly.finalproject.entities.Pictures;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PicturesMapper {

    PicturesDto toDto(Pictures pictures);

    Pictures toEntity(PicturesDto picturesDto);

    List<PicturesDto> toDtoList(List<Pictures> pictures);
}
