package net.lhm.projagile.Services.impl;

import net.lhm.projagile.Services.EpicService;
import net.lhm.projagile.dto.request.EpicDTO;
import net.lhm.projagile.dto.response.EpicResponseDTO;
import net.lhm.projagile.entities.Epic;
import net.lhm.projagile.entities.UserStory;
import net.lhm.projagile.Repositories.EpicRepository;
import net.lhm.projagile.Repositories.ProductBacklogRepository;
import net.lhm.projagile.Repositories.UserStoryRepository;
import net.lhm.projagile.mapper.EpicMapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EpicServiceImpl implements EpicService {
    
    private static final Logger logger = LoggerFactory.getLogger(EpicServiceImpl.class);

    private EpicRepository epicRepository;
    private UserStoryRepository userStoryRepository;
    private ProductBacklogRepository productBacklogRepository;
    private EpicMapper epicMapper;

    public EpicServiceImpl(EpicRepository epicRepository, UserStoryRepository userStoryRepository, 
                          ProductBacklogRepository productBacklogRepository, EpicMapper epicMapper) {
        this.epicRepository = epicRepository;
        this.userStoryRepository = userStoryRepository;
        this.productBacklogRepository = productBacklogRepository;
        this.epicMapper = epicMapper;
    }

    @Override
    public void createEpic(EpicDTO epicDTO) {
        logger.info("Creating epic with title: {}", epicDTO.getTitle());
        Epic epic = epicMapper.toEntity(epicDTO);

        var productBacklog = productBacklogRepository.findById(epicDTO.getProductBacklogId())
                .orElseThrow(() -> new EntityNotFoundException("ProductBacklog not found with id: " + epicDTO.getProductBacklogId()));
        epic.setProductBacklog(productBacklog);
        
        epicRepository.save(epic);
        logger.info("Epic created successfully with ID: {}", epic.getId());
    }

    @Override
    public void updateEpic(long id, EpicDTO epicDTO) {
        logger.info("Updating epic with ID: {}", id);
        Epic existingEpic = epicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Epic not found with id: " + id));

        existingEpic.setTitle(epicDTO.getTitle());
        existingEpic.setDescription(epicDTO.getDescription());

        if (existingEpic.getProductBacklog() == null || 
            !existingEpic.getProductBacklog().getId().equals(epicDTO.getProductBacklogId())) {
            var productBacklog = productBacklogRepository.findById(epicDTO.getProductBacklogId())
                    .orElseThrow(() -> new EntityNotFoundException("ProductBacklog not found with id: " + epicDTO.getProductBacklogId()));
            existingEpic.setProductBacklog(productBacklog);
        }

        epicRepository.save(existingEpic);
        logger.info("Epic updated successfully with ID: {}", id);
    }

    @Override
    public void deleteEpic(long id) {
        logger.info("Deleting epic with ID: {}", id);
        Epic epic = epicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Epic not found with id: " + id));

        epic.getUserStories().stream().forEach(story -> {
            story.setEpic(null);
            userStoryRepository.save(story);
        });
        epicRepository.delete(epic);
        logger.info("Epic deleted successfully with ID: {}", id);
    }

    @Override
    public Page<EpicResponseDTO> getAllEpics(Pageable pageable) {
        logger.info("Fetching all epics with pagination");
        Page<EpicResponseDTO> epics = epicRepository.findAll(pageable)
                .map(epicMapper::toResponseDTO);
        logger.info("Found {} epics", epics.getTotalElements());
        return epics;
    }

    @Override
    public EpicResponseDTO getEpicById(long id) {
        logger.info("Fetching epic by ID: {}", id);
        Epic epic = epicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Epic not found with id: " + id));
        
        EpicResponseDTO dto = epicMapper.toResponseDTO(epic);
        logger.info("Epic found with ID: {}", id);
        return dto;
    }

    @Override
    @Transactional
    public void addUserStoryToEpic(long id, long[] userStoryIds) {
        logger.info("Adding user stories to epic with ID: {}", id);
        Epic epic = epicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Epic not found with id: " + id));

        for (long userStoryId : userStoryIds) {
            UserStory userStory = userStoryRepository.findById(userStoryId)
                    .orElseThrow(() -> new EntityNotFoundException("UserStory not found with id: " + userStoryId));
            userStory.setEpic(epic);
            userStoryRepository.save(userStory);
        }
        
        logger.info("Added {} user stories to epic with ID: {}", userStoryIds.length, id);
    }

    @Override
    @Transactional
    public void deleteUserStoryFromEpic(long id, long[] userStoryIds) {
        logger.info("Removing user stories from epic with ID: {}", id);
        Epic epic = epicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Epic not found with id: " + id));

        for (long userStoryId : userStoryIds) {
            UserStory userStory = userStoryRepository.findById(userStoryId)
                    .orElseThrow(() -> new EntityNotFoundException("UserStory not found with id: " + userStoryId));
            userStory.setEpic(null);
            userStoryRepository.save(userStory);
        }
        
        logger.info("Removed {} user stories from epic with ID: {}", userStoryIds.length, id);
    }
}