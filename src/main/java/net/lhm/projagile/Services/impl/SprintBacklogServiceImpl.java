package net.lhm.projagile.Services.impl;

import net.lhm.projagile.Services.SprintBacklogService;
import net.lhm.projagile.dto.request.SprintBacklogDTO;
import net.lhm.projagile.dto.response.SprintBacklogResponseDTO;
import net.lhm.projagile.entities.SprintBacklog;
import net.lhm.projagile.entities.UserStory;
import net.lhm.projagile.Repositories.SprintBacklogRepository;
import net.lhm.projagile.Repositories.UserStoryRepository;
import net.lhm.projagile.Repositories.ProductBacklogRepository;
import net.lhm.projagile.mapper.SprintBacklogMapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SprintBacklogServiceImpl implements SprintBacklogService {

    private static final Logger logger = LoggerFactory.getLogger(SprintBacklogServiceImpl.class);

    private SprintBacklogRepository sprintBacklogRepository;
    private UserStoryRepository userStoryRepository;
    private ProductBacklogRepository productBacklogRepository;
    private SprintBacklogMapper sprintBacklogMapper;

    public SprintBacklogServiceImpl(SprintBacklogRepository sprintBacklogRepository, 
                                   UserStoryRepository userStoryRepository,
                                   ProductBacklogRepository productBacklogRepository,
                                   SprintBacklogMapper sprintBacklogMapper) {
        this.sprintBacklogRepository = sprintBacklogRepository;
        this.userStoryRepository = userStoryRepository;
        this.productBacklogRepository = productBacklogRepository;
        this.sprintBacklogMapper = sprintBacklogMapper;
    }

    @Override
    public void createSprintBacklog(SprintBacklogDTO sprintBacklogDTO) {
        logger.info("Creating sprint backlog with title: {}", sprintBacklogDTO.getTitle());
        SprintBacklog sprintBacklog = sprintBacklogMapper.toEntity(sprintBacklogDTO);

        var productBacklog = productBacklogRepository.findById(sprintBacklogDTO.getProductBacklogId())
                .orElseThrow(() -> new EntityNotFoundException("ProductBacklog not found with id: " + sprintBacklogDTO.getProductBacklogId()));
        sprintBacklog.setProductBacklog(productBacklog);
        
        sprintBacklogRepository.save(sprintBacklog);
        logger.info("Sprint backlog created successfully with ID: {}", sprintBacklog.getId());
    }

    @Override
    public void updateSprintBacklog(long id, SprintBacklogDTO sprintBacklogDTO) {
        logger.info("Updating sprint backlog with ID: {}", id);
        SprintBacklog existingSprintBacklog = sprintBacklogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SprintBacklog not found with id: " + id));

        existingSprintBacklog.setTitle(sprintBacklogDTO.getTitle());

        if (!existingSprintBacklog.getProductBacklog().getId().equals(sprintBacklogDTO.getProductBacklogId())) {
            var productBacklog = productBacklogRepository.findById(sprintBacklogDTO.getProductBacklogId())
                    .orElseThrow(() -> new EntityNotFoundException("ProductBacklog not found with id: " + sprintBacklogDTO.getProductBacklogId()));
            existingSprintBacklog.setProductBacklog(productBacklog);
        }

        sprintBacklogRepository.save(existingSprintBacklog);
        logger.info("Sprint backlog updated successfully with ID: {}", id);
    }

    @Override
    public void deleteSprintBacklog(long id) {
        logger.info("Deleting sprint backlog with ID: {}", id);
        SprintBacklog sprintBacklog = sprintBacklogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SprintBacklog not found with id: " + id));
        sprintBacklog.getUserStories().stream().forEach(story -> {
            story.setSprintBacklog(null);
            userStoryRepository.save(story);
        });
        sprintBacklogRepository.delete(sprintBacklog);
        logger.info("Sprint backlog deleted successfully with ID: {}", id);
    }

    @Override
    public List<SprintBacklogResponseDTO> getAllSprintBacklog() {
        logger.info("Fetching all sprint backlogs");
        List<SprintBacklogResponseDTO> sprintBacklogs = sprintBacklogRepository.findAll().stream()
                .map(sprintBacklogMapper::toResponseDTO)
                .collect(Collectors.toList());
        logger.info("Found {} sprint backlogs", sprintBacklogs.size());
        return sprintBacklogs;
    }

    @Override
    public SprintBacklogResponseDTO getSprintBacklogById(long id) {
        logger.info("Fetching sprint backlog by ID: {}", id);
        SprintBacklog sprintBacklog = sprintBacklogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SprintBacklog not found with id: " + id));
        
        SprintBacklogResponseDTO dto = sprintBacklogMapper.toResponseDTO(sprintBacklog);
        logger.info("Sprint backlog found with ID: {}", id);
        return dto;
    }

    @Override
    @Transactional
    public void addUserStoryToSprintBacklog(long id, int[] userStoryIds) {
        logger.info("Adding user stories to sprint backlog with ID: {}", id);
        SprintBacklog sprintBacklog = sprintBacklogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SprintBacklog not found with id: " + id));

        for (int userStoryId : userStoryIds) {
            UserStory userStory = userStoryRepository.findById((long) userStoryId)
                    .orElseThrow(() -> new EntityNotFoundException("UserStory not found with id: " + userStoryId));
            userStory.setSprintBacklog(sprintBacklog);
            userStoryRepository.save(userStory);
        }
        
        logger.info("Added {} user stories to sprint backlog with ID: {}", userStoryIds.length, id);
    }

    @Override
    @Transactional
    public void deleteUserStoryFromSprintBacklog(long id, int[] userStoryIds) {
        logger.info("Removing user stories from sprint backlog with ID: {}", id);
        SprintBacklog sprintBacklog = sprintBacklogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SprintBacklog not found with id: " + id));

        for (int userStoryId : userStoryIds) {
            UserStory userStory = userStoryRepository.findById((long) userStoryId)
                    .orElseThrow(() -> new EntityNotFoundException("UserStory not found with id: " + userStoryId));
            userStory.setSprintBacklog(null);
            userStoryRepository.save(userStory);
        }
        
        logger.info("Removed {} user stories from sprint backlog with ID: {}", userStoryIds.length, id);
    }

    @Override
    public void endOfSprintBacklog(long id, Date date) {
        logger.info("Ending sprint backlog with ID: {} on date: {}", id, date);
        SprintBacklog sprintBacklog = sprintBacklogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SprintBacklog not found with id: " + id));

        sprintBacklog.setDateEnd(date);
        sprintBacklogRepository.save(sprintBacklog);
        logger.info("Sprint backlog ended successfully with ID: {}", id);
    }
}