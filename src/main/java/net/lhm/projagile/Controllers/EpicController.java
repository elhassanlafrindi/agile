package net.lhm.projagile.Controllers;

import net.lhm.projagile.Services.EpicService;
import net.lhm.projagile.entities.Epic;
import net.lhm.projagile.entities.UserStory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/epics")
public class EpicController {
    @Autowired
    private EpicService epicService;


    @PostMapping("/add")
    public ResponseEntity<Epic> addEpic(@RequestParam Integer id, @RequestParam String titre, @RequestParam String description) {
        Epic newEpic = epicService.addEpic(id, titre, description);
        return ResponseEntity.ok(newEpic);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateEpic(@PathVariable Integer id, @RequestParam String titre, @RequestParam String description) {
        epicService.updateEpic(id,titre, description);
        return ResponseEntity.ok("Epic mis à jour avec succès !");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEpic(@PathVariable Integer id) {
        epicService.deleteEpic(id);
        return ResponseEntity.ok("Epic supprimé avec succès !");
    }


    @PostMapping("/{epicId}/addUserStory")
    public ResponseEntity<String> addUserStory(@PathVariable Integer epicId, @RequestBody UserStory userStory) {
        epicService.addUserStory(userStory);
        return ResponseEntity.ok("UserStory ajoutée à l'Epic !");
    }


    @DeleteMapping("/{epicId}/removeUserStory")
    public ResponseEntity<String> removeUserStory(@PathVariable Integer epicId, @RequestBody UserStory userStory) {
        epicService.removeUserStory(userStory);
        return ResponseEntity.ok("UserStory supprimée de l'Epic !");
    }


    @GetMapping("/all")
    public ResponseEntity<List<Epic>> getAllEpics() {
        return ResponseEntity.ok(epicService.getAllEpics());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Epic> getEpicById(@PathVariable Integer id) {
        return ResponseEntity.ok(epicService.getEpicById(id));
    }
}
