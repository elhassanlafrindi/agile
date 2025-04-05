package net.lhm.projagile.Controllers;

import net.lhm.projagile.Services.TaskService;
import net.lhm.projagile.entities.Statut;
import net.lhm.projagile.entities.Task;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Task addTask(@RequestParam Integer id, @RequestParam String titre, @RequestParam String description) {
        return taskService.addTask(id, titre, description);
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable Integer id, @RequestParam String titre, @RequestParam String description) {
        taskService.updateTask(id, titre, description);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
    }

    @PatchMapping("/{id}/statut")
    public void setStatut(@PathVariable Integer id, @RequestParam Statut statut) {
        taskService.setStatut(id, statut);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Integer id) {
        return taskService.getTaskById(id);
    }
}
