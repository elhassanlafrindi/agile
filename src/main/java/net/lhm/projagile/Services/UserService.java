package net.lhm.projagile.Services;

import net.lhm.projagile.dto.request.UserDTO;
import net.lhm.projagile.dto.response.UserResponseDTO;
import net.lhm.projagile.entities.Role;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface UserService {
    void createUser(UserDTO userDTO);
    void updateUser(UserDTO userDTO,long id);
    void deleteUser(long id);
    Page<UserResponseDTO> getAllUsers(Pageable pageable);
    UserResponseDTO getUser(long id);
    void changePassword(long id, String newPassword);
    void activateUser(long id);
    void changeUserRole(long id, Role newRole); // New method to change user role
}