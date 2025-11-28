package net.lhm.projagile.mapper;

import net.lhm.projagile.dto.request.TaskDTO;
import net.lhm.projagile.dto.response.TaskResponseDTO;
import net.lhm.projagile.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "statut", expression = "java(net.lhm.projagile.entities.Statut.ToDo)")
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "userStory", ignore = true)
    Task toEntity(TaskDTO dto);

    TaskResponseDTO toResponseDTO(Task task);
}