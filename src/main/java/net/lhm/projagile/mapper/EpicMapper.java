package net.lhm.projagile.mapper;

import net.lhm.projagile.dto.request.EpicDTO;
import net.lhm.projagile.dto.response.EpicResponseDTO;
import net.lhm.projagile.entities.Epic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EpicMapper {
    EpicMapper INSTANCE = Mappers.getMapper(EpicMapper.class);

    @Mapping(target = "productBacklog", ignore = true)
    Epic toEntity(EpicDTO dto);

    EpicResponseDTO toResponseDTO(Epic epic);
}