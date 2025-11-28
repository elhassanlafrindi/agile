package net.lhm.projagile.mapper;

import net.lhm.projagile.dto.request.ProductBacklogDTO;
import net.lhm.projagile.dto.response.ProductBacklogResponseDTO;
import net.lhm.projagile.entities.ProductBacklog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductBacklogMapper {
    ProductBacklogMapper INSTANCE = Mappers.getMapper(ProductBacklogMapper.class);
    @Mapping(target ="dateCreated", expression = "java(new java.util.Date())")
    ProductBacklog toEntity(ProductBacklogDTO dto);

    ProductBacklogResponseDTO toResponseDTO(ProductBacklog productBacklog);
}