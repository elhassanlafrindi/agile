package net.lhm.projagile.mapper;

import net.lhm.projagile.dto.request.UserDTO;
import net.lhm.projagile.dto.response.UserResponseDTO;
import net.lhm.projagile.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(target = "isActive", constant = "false")
    @Mapping(target = "password", ignore = true) 
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdBy", ignore = true)
    User toEntity(UserDTO dto);

    @Mapping(target = "createdByUser", expression = "java(user.getCreatedBy() != null ? user.getCreatedBy().getEmail() : null)")
    UserResponseDTO toResponseDTO(User user);
}