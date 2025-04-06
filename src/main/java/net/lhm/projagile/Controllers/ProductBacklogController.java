package net.lhm.projagile.Controllers;

import jakarta.validation.Valid;
import net.lhm.projagile.Services.ProductBacklogService;
import net.lhm.projagile.dto.ProductBacklogDTO;
import net.lhm.projagile.entities.ProductBacklog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/agile/productBacklogs")
public class ProductBacklogController {
    private final ProductBacklogService productBacklogService;

    public ProductBacklogController(ProductBacklogService productBacklogService) {
        this.productBacklogService = productBacklogService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody ProductBacklogDTO productBacklogDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            ProductBacklog createdProductBacklog = productBacklogService.createProductBacklog(productBacklogDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProductBacklog);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody ProductBacklogDTO productBacklogDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            ProductBacklog updatedProductBacklog = productBacklogService.updateProductBacklog(id, productBacklogDTO);
            return ResponseEntity.ok(updatedProductBacklog);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ProductBacklog non trouvé !");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            productBacklogService.deleteProductBacklog(id);
            return ResponseEntity.ok("ProductBacklog supprimé avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ProductBacklog non trouvé !");
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductBacklog>> getAll() {
        List<ProductBacklog> all = productBacklogService.getAllProductBacklogs();
        return all.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductBacklog> getById(@PathVariable Integer id) {
        try {
            ProductBacklog productBacklog = productBacklogService.getProductBacklogById(id);
            return ResponseEntity.ok(productBacklog);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
