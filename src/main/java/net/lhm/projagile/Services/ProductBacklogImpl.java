package net.lhm.projagile.Services;

import net.lhm.projagile.Repositories.EpicRepo;
import net.lhm.projagile.Repositories.ProductBacklogRepo;
import net.lhm.projagile.Repositories.UserStoryRepo;
import net.lhm.projagile.entities.Epic;
import net.lhm.projagile.entities.ProductBacklog;
import net.lhm.projagile.entities.UserStory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

@Service
public class ProductBacklogImpl implements ProductBacklogService {
    @Autowired
    private ProductBacklogRepo productBacklogRepository;
    @Autowired
    private UserStoryRepo userStoryRepository;

    @Autowired
    private EpicRepo epicRepository;

    @Override
    public ProductBacklog addProductBacklog(Integer id, String nom) {
        ProductBacklog productBacklog = new ProductBacklog();
        productBacklog.setId(id);
        productBacklog.setNom(nom);
        return productBacklogRepository.save(productBacklog);
    }
    @Override
    public void updateProductBacklog(String nom) {
        Optional<ProductBacklog> existingBacklog = productBacklogRepository.findById(1);
        if (existingBacklog.isPresent()) {
            ProductBacklog backlog = existingBacklog.get();
            backlog.setNom(nom);
            productBacklogRepository.save(backlog);
        } else {
            throw new RuntimeException("Product Backlog non trouvé !");
        }
    }

    @Override
    public void deleteProductBacklog() {
        productBacklogRepository.deleteAll();
    }

    @Override
    public void addUserStory(UserStory userStory) {
        Optional<ProductBacklog> existingBacklog = productBacklogRepository.findById(1);
        if (existingBacklog.isPresent()) {
            ProductBacklog backlog = existingBacklog.get();
            backlog.getUserStories().add(userStory);
            userStory.setProductBacklog(backlog);
            userStoryRepository.save(userStory);
            productBacklogRepository.save(backlog);
        } else {
            throw new RuntimeException("Product Backlog non trouvé !");
        }
    }

    @Override
    public void removeUserStory(UserStory userStory) {
        Optional<ProductBacklog> existingBacklog = productBacklogRepository.findById(1);
        if (existingBacklog.isPresent()) {
            ProductBacklog backlog = existingBacklog.get();
            backlog.getUserStories().remove(userStory);
            userStoryRepository.delete(userStory);
            productBacklogRepository.save(backlog);
        } else {
            throw new RuntimeException("Product Backlog non trouvé !");
        }
    }
//
//    @Override
//    public void addEpic(Epic epic) {
//        Optional<ProductBacklog> existingBacklog = productBacklogRepository.findById(1);
//
//        if (existingBacklog.isPresent()) {
//            ProductBacklog backlog = existingBacklog.get();
//
//
//            epic.setProductBacklog(backlog);
//
//            // Sauvegarder uniquement l'Epic (JPA mettra à jour la relation)
//            epicRepository.save(epic);
//        } else {
//            throw new RuntimeException("Product Backlog non trouvé !");
//        }
//    }




    @Override
    public void removeEpic(Epic epic) {
        if (epicRepository.existsById(epic.getId())) {
            epicRepository.deleteById(epic.getId());
        } else {
            throw new RuntimeException("Epic non trouvé !");
        }
    }

    @Override
    public void prioritizeUserStories() {
        Optional<ProductBacklog> existingBacklog = productBacklogRepository.findById(1);
        if (existingBacklog.isPresent()) {
            ProductBacklog backlog = existingBacklog.get();
            backlog.getUserStories().sort(Comparator.comparingInt(UserStory::getPriorite));
            productBacklogRepository.save(backlog);
        } else {
            throw new RuntimeException("Product Backlog non trouvé !");
        }
    }

}
