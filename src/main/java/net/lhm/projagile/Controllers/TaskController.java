package net.lhm.projagile.Controllers;

import jakarta.validation.Valid;
import net.lhm.projagile.Services.TaskService;
import net.lhm.projagile.Services.UtilisateurService;
import net.lhm.projagile.dto.TaskDTO;
import net.lhm.projagile.dtoResponse.TaskDTORes;
import net.lhm.projagile.entities.Statut;
import net.lhm.projagile.entities.Task;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/agile/tasks")
@PreAuthorize("hasRole('SCRUM_MASTER')")
public class TaskController {
    private final TaskService taskService;
    private final UtilisateurService utilisateurService;
    public TaskController(TaskService taskService, UtilisateurService utilisateurService) {
        this.taskService = taskService;
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<?> create(@PathVariable Integer id,@Valid @RequestBody TaskDTO taskDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        try {
             taskService.createTask(id,taskDTO);
            return ResponseEntity.ok("Task "+taskDTO.getTitre()+" created successfully");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SCRUM_MASTER','DEVELOPER')")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody TaskDTO taskDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            Task updatedTask = taskService.updateTask(id, taskDTO);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<TaskDTORes>> getTasksByUtilisateur(@PathVariable Integer userId) {
        List<TaskDTORes> tasks = taskService.getTasksByUtilisateur(userId);
        if (tasks == null || tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.ok("Task supprimée avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erreur: Task non trouvée !");
        }
    }

    @GetMapping
    public ResponseEntity<List<TaskDTORes>> getAll() {
        List<TaskDTORes> all = taskService.getAllTasks();
        return all.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTORes> getById(@PathVariable Integer id) {
        TaskDTORes task = taskService.getTaskById(id);
        return task == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(task);
    }

    @GetMapping("/by-statut")
    public ResponseEntity<List<TaskDTORes>> getUsingStatut(@RequestParam(required = true) Statut statut) {
        List<TaskDTORes> all = taskService.getByStatus(statut);
        return all.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(all);
    }
    @PostMapping("{id}/affect/{idutilisateur}")
    public ResponseEntity<String> addTask(@PathVariable Integer id, @PathVariable Integer idutilisateur) {
        try{
                    taskService.affecttaskToUtilisateur(id,idutilisateur);
                    return ResponseEntity.ok("Task added successfully to utilisateur"+utilisateurService.getUtilisateurById(idutilisateur).getNom());
        }catch (RuntimeException    e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad request");
        }
    }
}
