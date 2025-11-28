package net.lhm.projagile.Services;

import net.lhm.projagile.dto.request.ProductBacklogDTO;
import net.lhm.projagile.dto.response.ProductBacklogResponseDTO;

import java.util.List;

public interface ProductBacklogService {

    void createProductBacklog(ProductBacklogDTO productBacklogDTO);
    void updateProductBacklog(long id, ProductBacklogDTO productBacklogDTO);
    void deleteProductBacklog(long id);
    List<ProductBacklogResponseDTO> getAllProductBacklog();
    ProductBacklogResponseDTO getProductBacklogById(long id);
}