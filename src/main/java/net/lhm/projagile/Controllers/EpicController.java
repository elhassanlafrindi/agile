package net.lhm.projagile.Controllers;

import jakarta.validation.Valid;
import net.lhm.projagile.Services.EpicService;
import net.lhm.projagile.dto.EpicDTO;
import net.lhm.projagile.entities.Epic;
import net.lhm.projagile.entities.UserStory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/agile/epics")
@PreAuthorize("hasRole('PRODUCT_OWNER')")
public class EpicController {
    @Autowired
    private EpicService epicService;


    @PostMapping("/create")
    public ResponseEntity<?> addEpic(@Valid @RequestBody EpicDTO epicDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> error = result.getAllErrors().stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
       try{ Epic newEpic = epicService.addEpic(epicDTO);
        return ResponseEntity.ok("Epic added successfully.");}
       catch (DataIntegrityViolationException e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data: " + e.getMessage()); // 400 Bad Request
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred."); // 500 Internal Server Error
       }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateEpic(@PathVariable Integer id,@Valid @RequestBody EpicDTO epicDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> error = result.getAllErrors().stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

            epicService.updateEpic(id,epicDTO);
            return ResponseEntity.ok("Epic mis à jour avec succès !");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEpic(@PathVariable Integer id) {
       try{ epicService.deleteEpic(id);
        return ResponseEntity.ok("Epic supprimé avec succès !");}
       catch (RuntimeException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
       }
    }


    @PostMapping("/{epicId}/userStories/{userStoryId}")
    public ResponseEntity<String> addUserStory(@PathVariable int epicId, @PathVariable int userStoryId) {
        try {
            epicService.addUserStory(userStoryId, epicId);
            return ResponseEntity.ok("UserStory ajoutée à l'Epic avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur s'est produite lors de l'ajout de la UserStory à l'Epic");
        }
    }

    @DeleteMapping("/{epicId}/userStories/{userStoryId}")
    public ResponseEntity<String> removeUserStory(@PathVariable int epicId, @PathVariable int userStoryId) {
        try {
            epicService.removeUserStory(userStoryId, epicId);
            return ResponseEntity.ok("UserStory retirée de l'Epic avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur s'est produite lors du retrait de la UserStory de l'Epic");
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Epic>> getAllEpics() {
        List<Epic> epics=epicService.getAllEpics();
        return epics.isEmpty() ?ResponseEntity.noContent().build() : ResponseEntity.ok(epics);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getEpicById(@PathVariable Integer id) {
        try{
            Epic epic=epicService.getEpicById(id);
        return ResponseEntity.ok(epic);
        }
        catch (RuntimeException e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
