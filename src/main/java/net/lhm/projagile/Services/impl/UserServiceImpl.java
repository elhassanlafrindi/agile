package net.lhm.projagile.Services.impl;

import net.lhm.projagile.Services.UserService;
import net.lhm.projagile.dto.request.UserDTO;
import net.lhm.projagile.dto.response.UserResponseDTO;
import net.lhm.projagile.entities.Role;
import net.lhm.projagile.entities.User;
import net.lhm.projagile.Repositories.UserRepository;
import net.lhm.projagile.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(UserDTO userDTO) {
        logger.info("Creating user with email: {}", userDTO.getEmail());

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with email " + userDTO.getEmail() + " already exists");
        }
        
        User user = userMapper.toEntity(userDTO);

        User createdBy = userRepository.findById(userDTO.getCreatedBy())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userDTO.getCreatedBy()));
        user.setCreatedBy(createdBy);

        String encodedPassword = passwordEncoder.encode(userDTO.getEmail());
        user.setPassword(encodedPassword);
        
        userRepository.save(user);
        logger.info("User created successfully with ID: {}", user.getId());
    }

    @Override
    public void updateUser(UserDTO userDTO, long id) {
        logger.info("Updating user with ID: {}", id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setRole(userDTO.getRole());

        if (existingUser.getCreatedBy().getId() != userDTO.getCreatedBy()) {
            User createdBy = userRepository.findById(userDTO.getCreatedBy())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userDTO.getCreatedBy()));
            existingUser.setCreatedBy(createdBy);
        }

        userRepository.save(existingUser);
        logger.info("User updated successfully with ID: {}", id);
    }

    @Override
    public void deleteUser(long id) {
        logger.info("Deleting user with ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
        logger.info("User deleted successfully with ID: {}", id);
    }

    @Override
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        logger.info("Fetching all users with pagination");
        Page<UserResponseDTO> users = userRepository.findAll(pageable)
                .map(userMapper::toResponseDTO);
        logger.info("Found {} users", users.getTotalElements());
        return users;
    }

    @Override
    public UserResponseDTO getUser(long id) {
        logger.info("Fetching user by ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        
        UserResponseDTO dto = userMapper.toResponseDTO(user);
        logger.info("User found with ID: {}", id);
        return dto;
    }

    @Override
    public void changePassword(long id, String newPassword) {
        logger.info("Changing password for user with ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        
        userRepository.save(user);
        logger.info("Password changed successfully for user with ID: {}", id);
    }

    @Override
    public void changeUserRole(long id, Role newRole) {
        logger.info("Changing role for user with ID: {} to {}", id, newRole);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        user.setRole(newRole);
        userRepository.save(user);
        logger.info("Role changed successfully for user with ID: {}", id);
    }

    @Override
    public void activateUser(long id) {
        logger.info("Activating user with ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        user.setActive(true);
        userRepository.save(user);
        logger.info("User activated successfully with ID: {}", id);
    }
}