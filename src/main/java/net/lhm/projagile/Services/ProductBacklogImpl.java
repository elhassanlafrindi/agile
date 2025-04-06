package net.lhm.projagile.Services;

import jakarta.transaction.Transactional;
import net.lhm.projagile.Repositories.ProductBacklogRepo;
import net.lhm.projagile.dto.ProductBacklogDTO;
import net.lhm.projagile.entities.ProductBacklog;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductBacklogImpl implements ProductBacklogService {
    private final ProductBacklogRepo productBacklogRepo;

    public ProductBacklogImpl(ProductBacklogRepo productBacklogRepo) {
        this.productBacklogRepo = productBacklogRepo;
    }

    @Override
    @Transactional
    public ProductBacklog createProductBacklog(ProductBacklogDTO productBacklogDTO) {
        return productBacklogRepo.save(ProductBacklog.builder()
                .nom(productBacklogDTO.getNom())
                .build());
    }

    @Override
    public ProductBacklog updateProductBacklog(Integer id, ProductBacklogDTO productBacklogDTO) {
        Optional<ProductBacklog> productBacklogOptional = productBacklogRepo.findById(id);
        if (productBacklogOptional.isPresent()) {
            ProductBacklog productBacklog = productBacklogOptional.get();
            productBacklog.setNom(productBacklogDTO.getNom());
            return productBacklogRepo.save(productBacklog);
        }
        throw new RuntimeException("ProductBacklog non trouvé !");
    }

    @Override
    public void deleteProductBacklog(Integer id) {
        if (!productBacklogRepo.existsById(id)) {
            throw new RuntimeException("ProductBacklog non trouvé !");
        }
        productBacklogRepo.deleteById(id);
    }

    @Override
    public List<ProductBacklog> getAllProductBacklogs() {
        return productBacklogRepo.findAll();
    }

    @Override
    public ProductBacklog getProductBacklogById(Integer id) {
        return productBacklogRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductBacklog non trouvé !"));
    }


}
