package net.lhm.projagile.Services;

import net.lhm.projagile.dto.ProductBacklogDTO;
import net.lhm.projagile.entities.Epic;
import net.lhm.projagile.entities.ProductBacklog;
import net.lhm.projagile.entities.UserStory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductBacklogService {
    ProductBacklog createProductBacklog(ProductBacklogDTO productBacklogDTO);
    ProductBacklog updateProductBacklog(Integer id, ProductBacklogDTO productBacklogDTO);
    void deleteProductBacklog(Integer id);
    List<ProductBacklog> getAllProductBacklogs();
    ProductBacklog getProductBacklogById(Integer id);
    void removeUserStoryFromProductBacklog(int idProductBacklog, int idUserStory);
    void addUserStorytoProductBacklog( int idProductBacklog, int idUserStory);
}
