package net.lhm.projagile.Services.impl;

import net.lhm.projagile.Services.UserStoryService;
import net.lhm.projagile.dto.request.UserStoryDTO;
import net.lhm.projagile.dto.response.UserStoryResponseDTO;
import net.lhm.projagile.entities.Priority;
import net.lhm.projagile.entities.Statut;
import net.lhm.projagile.entities.UserStory;
import net.lhm.projagile.entities.ProductBacklog;
import net.lhm.projagile.Repositories.UserStoryRepository;
import net.lhm.projagile.Repositories.ProductBacklogRepository;
import net.lhm.projagile.mapper.UserStoryMapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserStoryServiceImpl implements UserStoryService {

    private static final Logger logger = LoggerFactory.getLogger(UserStoryServiceImpl.class);

    private UserStoryRepository userStoryRepository;

    private ProductBacklogRepository productBacklogRepository;

    private UserStoryMapper userStoryMapper;

    public UserStoryServiceImpl(UserStoryRepository userStoryRepository, ProductBacklogRepository productBacklogRepository, UserStoryMapper userStoryMapper) {
        this.userStoryRepository = userStoryRepository;
        this.productBacklogRepository = productBacklogRepository;
        this.userStoryMapper = userStoryMapper;
    }

    @Override
    public void createUserStory(UserStoryDTO userStoryDTO) {
        logger.info("Creating user story with title: {}", userStoryDTO.getTitle());
        ProductBacklog productBacklog = productBacklogRepository.findById(userStoryDTO.getProductBacklogId())
                .orElseThrow(() -> new EntityNotFoundException("ProductBacklog not found with id: " + userStoryDTO.getProductBacklogId()));

        UserStory userStory = userStoryMapper.toEntity(userStoryDTO);
        userStory.setProductBacklog(productBacklog);
        userStoryRepository.save(userStory);
        logger.info("User story created successfully with ID: {}", userStory.getId());
    }

    @Override
    public void updateUserStory(int userStoryID, UserStoryDTO userStoryDTO) {
        logger.info("Updating user story with ID: {}", userStoryID);
        UserStory existingUserStory = userStoryRepository.findById((long) userStoryID)
                .orElseThrow(() -> new EntityNotFoundException("UserStory not found with id: " + userStoryID));

        ProductBacklog productBacklog = productBacklogRepository.findById(userStoryDTO.getProductBacklogId())
                .orElseThrow(() -> new EntityNotFoundException("ProductBacklog not found with id: " + userStoryDTO.getProductBacklogId()));

        existingUserStory.setTitle(userStoryDTO.getTitle());
        existingUserStory.setDescription(userStoryDTO.getDescription());
        existingUserStory.setPriority(userStoryDTO.getPriority());
        existingUserStory.setProductBacklog(productBacklog);

        userStoryRepository.save(existingUserStory);
        logger.info("User story updated successfully with ID: {}", userStoryID);
    }

    @Override
    public void deleteUserStory(int userStoryID) {
        logger.info("Deleting user story with ID: {}", userStoryID);
        UserStory userStory = userStoryRepository.findById((long) userStoryID)
                .orElseThrow(() -> new EntityNotFoundException("UserStory not found with id: " + userStoryID));

        userStoryRepository.delete(userStory);
        logger.info("User story deleted successfully with ID: {}", userStoryID);
    }

    @Override
    public void updateUserStoryStatut(int userStoryID, Statut statut) {
        logger.info("Updating status of user story with ID: {} to {}", userStoryID, statut);
        UserStory userStory = userStoryRepository.findById((long) userStoryID)
                .orElseThrow(() -> new EntityNotFoundException("UserStory not found with id: " + userStoryID));

        userStory.setStatut(statut);
        userStoryRepository.save(userStory);
        logger.info("User story status updated successfully with ID: {}", userStoryID);
    }

    @Override
    public List<UserStoryResponseDTO> getAllUserStory() {
        logger.info("Fetching all user stories");
        List<UserStoryResponseDTO> userStories = userStoryRepository.findAll().stream()
                .map(userStoryMapper::toResponseDTO)
                .collect(Collectors.toList());
        logger.info("Found {} user stories", userStories.size());
        return userStories;
    }

    @Override
    public List<UserStoryResponseDTO> getAllUserStoryByStatus(Statut statut) {
        logger.info("Fetching user stories by status: {}", statut);
        List<UserStoryResponseDTO> userStories = userStoryRepository.findByStatut(statut).stream()
                .map(userStoryMapper::toResponseDTO)
                .collect(Collectors.toList());
        logger.info("Found {} user stories with status: {}", userStories.size(), statut);
        return userStories;
    }

    @Override
    public UserStoryResponseDTO getUserStoryById(int id) {
        logger.info("Fetching user story by ID: {}", id);
        UserStory userStory = userStoryRepository.findById((long) id)
                .orElseThrow(() -> new EntityNotFoundException("UserStory not found with id: " + id));
        
        UserStoryResponseDTO dto = userStoryMapper.toResponseDTO(userStory);
        logger.info("User story found with ID: {}", id);
        return dto;
    }

    @Override
    public List<UserStoryResponseDTO> getAllUserStoryByPriority(Priority priority) {
        logger.info("Fetching user stories by priority: {}", priority);
        List<UserStoryResponseDTO> userStories = userStoryRepository.findAllByPriority(priority).stream()
                .map(userStoryMapper::toResponseDTO)
                .collect(Collectors.toList());
        logger.info("Found {} user stories with priority: {}", userStories.size(), priority);
        return userStories;
    }
}
