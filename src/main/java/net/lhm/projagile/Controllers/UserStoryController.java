package net.lhm.projagile.Controllers;

import jakarta.validation.Valid;
import net.lhm.projagile.Services.UserStoryService;
import net.lhm.projagile.dto.UserStoryDTO;
import net.lhm.projagile.dtoResponse.UserStoryDTORes;
import net.lhm.projagile.entities.Statut;
import net.lhm.projagile.entities.UserStory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("/agile/userStories")
@PreAuthorize("hasRole('PRODUCT_OWNER')")
public class UserStoryController {
    private final UserStoryService userStoryService;

    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody UserStoryDTO userStoryDTO , BindingResult result) {
        if (result.hasErrors()) {
            // Collect the error messages and return them as the response body
            List<String> error = result.getAllErrors().stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());

            // Return 400 Bad Request with the error list in the response body
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        try {
            UserStory createdUserStory = userStoryService.createUserStory(userStoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUserStory); // 201 Created
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data: " + e.getMessage()); // 400 Bad Request
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred."); // 500 Internal Server Error
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,@Valid @RequestBody UserStoryDTO userStoryDTO, BindingResult result) {
        if(result.hasErrors()) {
            List<String> error=result.getAllErrors().stream().map(errore->errore.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        try {
            UserStory updatedUserStory = userStoryService.updateUserStory(id, userStoryDTO);
            return ResponseEntity.ok(updatedUserStory);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Returns 404 if not found
        }   }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            userStoryService.deleteUserStory(id);
            return ResponseEntity.ok("UserStory supprimée avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erreur: UserStory non trouvée !");
        }
    }

    @GetMapping

    public ResponseEntity<List<UserStoryDTORes>> getAll() {
        List<UserStoryDTORes> all=userStoryService.getAllUserStories();

        return all.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserStoryDTORes> getById(@PathVariable Integer id) {

        UserStoryDTORes userStory= userStoryService.getUserStoryById(id);
        return userStory==null ? ResponseEntity.notFound().build() :
                ResponseEntity.ok(userStory);
    }
    @GetMapping("/by-statut")

    public ResponseEntity<List<UserStoryDTORes>> getUsingStatut(@RequestParam(required = true) Statut statut) {
        List<UserStoryDTORes> all = userStoryService.getByStatus(statut);
        return all.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(all);
    }
    @PutMapping("/{id}/prioriser")
    public ResponseEntity<String> prioriser(@PathVariable Integer id, @RequestParam(required = true) int priority) {
        try {
            userStoryService.prioriserUserStory(id, priority);
            return ResponseEntity.ok("Priorité mise à jour avec succès !");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UserStory non trouvée !");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
