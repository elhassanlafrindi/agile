package net.lhm.projagile.Controllers;

import net.lhm.projagile.Services.ProductBacklogService;
import net.lhm.projagile.entities.Epic;
import net.lhm.projagile.entities.ProductBacklog;
import net.lhm.projagile.entities.UserStory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productbacklog")
public class ProductController {
    @Autowired
    private ProductBacklogService productBacklogService;

    @PostMapping("/add")
    public ResponseEntity<ProductBacklog> addProductBacklog(@RequestParam Integer id, @RequestParam String nom) {
        return ResponseEntity.ok(productBacklogService.addProductBacklog(id, nom));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProductBacklog() {
        productBacklogService.deleteProductBacklog();
        return ResponseEntity.ok("Product Backlog supprimé avec succès !");
    }

    @PostMapping("/addUserStory")
    public ResponseEntity<String> addUserStory(@RequestBody UserStory userStory) {
        productBacklogService.addUserStory(userStory);
        return ResponseEntity.ok("UserStory ajoutée au Product Backlog !");
    }

//    @PostMapping("/addEpic")
//    public ResponseEntity<String> addEpic(@RequestBody Epic epic) {
//        productBacklogService.addEpic(epic);
//        return ResponseEntity.ok("Epic ajoutée au Product Backlog !");
//    }
}
