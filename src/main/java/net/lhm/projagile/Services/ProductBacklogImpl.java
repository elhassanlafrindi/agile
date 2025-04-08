package net.lhm.projagile.Services;

import jakarta.transaction.Transactional;
import net.lhm.projagile.Repositories.ProductBacklogRepo;
import net.lhm.projagile.Repositories.UserStoryRepo;
import net.lhm.projagile.dto.ProductBacklogDTO;
import net.lhm.projagile.entities.ProductBacklog;
import net.lhm.projagile.entities.UserStory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductBacklogImpl implements ProductBacklogService {
    private final ProductBacklogRepo productBacklogRepo;
    private final UserStoryRepo userStoryRepo;
    public ProductBacklogImpl(ProductBacklogRepo productBacklogRepo, UserStoryRepo userStoryRepo) {
        this.productBacklogRepo = productBacklogRepo;
        this.userStoryRepo = userStoryRepo;
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

    @Override
    public void addUserStorytoProductBacklog(int idProductBacklog, int idUserStory) {
        Optional<UserStory> userStory=userStoryRepo.findById(idUserStory);
        Optional<ProductBacklog> productBacklog=productBacklogRepo.findById(idProductBacklog);
        if(userStory.isPresent() && productBacklog.isPresent()){
            UserStory userStory1=userStory.get();
            ProductBacklog productBacklog1=productBacklog.get();
            if (productBacklog1.getUserStories().contains(userStory1)) {
                throw new RuntimeException("this UserStory is already added !");
            }else{
                productBacklog1.getUserStories() .add(userStory1);
                userStory1.setProductBacklog(productBacklog1);
                userStoryRepo.save(userStory1);
                productBacklogRepo.save(productBacklog1);
            }

        }
        else {
            throw new RuntimeException("userStory is not found !");
        }
    }
    @Override
    public void removeUserStoryFromProductBacklog(int idProductBacklog, int idUserStory) {
        ProductBacklog productBacklog = productBacklogRepo.findById(idProductBacklog)
                .orElseThrow(() -> new RuntimeException("ProductBacklog not found!"));

        UserStory userStory = userStoryRepo.findById(idUserStory)
                .orElseThrow(() -> new RuntimeException("UserStory not found!"));

        if (!productBacklog.getUserStories().contains(userStory)) {
            throw new RuntimeException("UserStory not in this ProductBacklog!");
        }

        userStory.setProductBacklog(null);
        userStoryRepo.save(userStory);
    }


}
