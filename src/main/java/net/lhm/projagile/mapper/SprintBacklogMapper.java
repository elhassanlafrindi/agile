package net.lhm.projagile.mapper;

import net.lhm.projagile.dto.request.SprintBacklogDTO;
import net.lhm.projagile.dto.response.SprintBacklogResponseDTO;
import net.lhm.projagile.entities.SprintBacklog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SprintBacklogMapper {
    SprintBacklogMapper INSTANCE = Mappers.getMapper(SprintBacklogMapper.class);
    
    @Mapping(target = "dateCreated", expression = "java(new java.util.Date())")
    @Mapping(target = "productBacklog", ignore = true)
    SprintBacklog toEntity(SprintBacklogDTO dto);

    SprintBacklogResponseDTO toResponseDTO(SprintBacklog sprintBacklog);
}