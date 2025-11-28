package net.lhm.projagile.Services.impl;

import net.lhm.projagile.Services.ProductBacklogService;
import net.lhm.projagile.dto.request.ProductBacklogDTO;
import net.lhm.projagile.dto.response.ProductBacklogResponseDTO;
import net.lhm.projagile.entities.ProductBacklog;
import net.lhm.projagile.Repositories.ProductBacklogRepository;
import net.lhm.projagile.mapper.ProductBacklogMapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductBacklogServiceImpl implements ProductBacklogService {

    private static final Logger logger = LoggerFactory.getLogger(ProductBacklogServiceImpl.class);

    private ProductBacklogRepository productBacklogRepository;
    private ProductBacklogMapper productBacklogMapper;

    public ProductBacklogServiceImpl(ProductBacklogRepository productBacklogRepository, ProductBacklogMapper productBacklogMapper) {
        this.productBacklogRepository = productBacklogRepository;
        this.productBacklogMapper = productBacklogMapper;
    }

    @Override
    public void createProductBacklog(ProductBacklogDTO productBacklogDTO) {
        logger.info("Creating product backlog with title: {}", productBacklogDTO.getTitle());
        ProductBacklog productBacklog = productBacklogMapper.toEntity(productBacklogDTO);
        productBacklogRepository.save(productBacklog);
        logger.info("Product backlog created successfully with ID: {}", productBacklog.getId());
    }

    @Override
    public void updateProductBacklog(long id, ProductBacklogDTO productBacklogDTO) {
        logger.info("Updating product backlog with ID: {}", id);
        ProductBacklog existingProductBacklog = productBacklogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductBacklog not found with id: " + id));

        existingProductBacklog.setTitle(productBacklogDTO.getTitle());

        productBacklogRepository.save(existingProductBacklog);
        logger.info("Product backlog updated successfully with ID: {}", id);
    }

    @Override
    public void deleteProductBacklog(long id) {
        logger.info("Deleting product backlog with ID: {}", id);
        ProductBacklog productBacklog = productBacklogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductBacklog not found with id: " + id));

        productBacklogRepository.delete(productBacklog);
        logger.info("Product backlog deleted successfully with ID: {}", id);
    }

    @Override
    public List<ProductBacklogResponseDTO> getAllProductBacklog() {
        logger.info("Fetching all product backlogs");
        List<ProductBacklogResponseDTO> productBacklogs = productBacklogRepository.findAll().stream()
                .map(productBacklogMapper::toResponseDTO)
                .collect(Collectors.toList());
        logger.info("Found {} product backlogs", productBacklogs.size());
        return productBacklogs;
    }

    @Override
    public ProductBacklogResponseDTO getProductBacklogById(long id) {
        logger.info("Fetching product backlog by ID: {}", id);
        ProductBacklog productBacklog = productBacklogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductBacklog not found with id: " + id));
        
        ProductBacklogResponseDTO dto = productBacklogMapper.toResponseDTO(productBacklog);
        logger.info("Product backlog found with ID: {}", id);
        return dto;
    }
}