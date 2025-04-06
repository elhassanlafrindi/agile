package net.lhm.projagile.Controllers;

import jakarta.validation.Valid;
import net.lhm.projagile.Services.SprintBacklogService;
import net.lhm.projagile.dto.SprintBacklogDTO;
import net.lhm.projagile.entities.SprintBacklog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/agile/sprintBacklogs")
public class SprintBacklogController {
    private final SprintBacklogService sprintBacklogService;

    public SprintBacklogController(SprintBacklogService sprintBacklogService) {
        this.sprintBacklogService = sprintBacklogService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody SprintBacklogDTO sprintBacklogDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            SprintBacklog createdSprintBacklog = sprintBacklogService.createSprintBacklog(sprintBacklogDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSprintBacklog);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody SprintBacklogDTO sprintBacklogDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            SprintBacklog updatedSprintBacklog = sprintBacklogService.updateSprintBacklog(id, sprintBacklogDTO);
            return ResponseEntity.ok(updatedSprintBacklog);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SprintBacklog non trouvé !");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            sprintBacklogService.deleteSprintBacklog(id);
            return ResponseEntity.ok("SprintBacklog supprimé avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SprintBacklog non trouvé !");
        }
    }

    @GetMapping
    public ResponseEntity<List<SprintBacklog>> getAll() {
        List<SprintBacklog> all = sprintBacklogService.getAllSprintBacklogs();
        return all.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SprintBacklog> getById(@PathVariable Integer id) {
        try {
            SprintBacklog sprintBacklog = sprintBacklogService.getSprintBacklogById(id);
            return ResponseEntity.ok(sprintBacklog);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
