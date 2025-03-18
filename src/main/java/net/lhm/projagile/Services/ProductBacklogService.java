package net.lhm.projagile.Services;

import net.lhm.projagile.entities.Epic;
import net.lhm.projagile.entities.ProductBacklog;
import net.lhm.projagile.entities.UserStory;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBacklogService {
    ProductBacklog addProductBacklog(Integer id, String nom);
    void updateProductBacklog(String nom);
    void deleteProductBacklog();
    void addUserStory(UserStory userStory);
    void removeUserStory(UserStory userStory);
    //void addEpic(Epic epic);
    void removeEpic(Epic epic);
    void prioritizeUserStories();
}
