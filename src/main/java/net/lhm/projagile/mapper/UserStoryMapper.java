package net.lhm.projagile.mapper;

import net.lhm.projagile.dto.request.UserStoryDTO;
import net.lhm.projagile.dto.response.UserStoryResponseDTO;
import net.lhm.projagile.entities.UserStory;
import net.lhm.projagile.entities.Statut;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserStoryMapper {

    @Mapping(target = "statut", expression = "java(net.lhm.projagile.entities.Statut.ToDo)")
    @Mapping(target = "productBacklog", ignore = true)
    UserStory toEntity(UserStoryDTO dto);

    UserStoryResponseDTO toResponseDTO(UserStory userStory);
}