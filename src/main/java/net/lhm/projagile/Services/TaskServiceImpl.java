package net.lhm.projagile.Services;

import net.lhm.projagile.Repositories.TaskRepo;
import net.lhm.projagile.entities.Statut;
import net.lhm.projagile.entities.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService{
    private final TaskRepo taskRepo;

    public TaskServiceImpl(TaskRepo taskRepository) {
        this.taskRepo = taskRepository;
    }

    @Override
    public Task addTask(Integer id, String titre, String description) {
        Optional<Task> existingTask = taskRepo.findById(id);

        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            task.setTitre(titre);
            task.setDescription(description);
            return taskRepo.save(task);
        } else {
            throw new RuntimeException("Task avec ID " + id + " non trouvée !");
        }
    }

    @Override
    public void updateTask(Integer id, String titre, String description) {
        Optional<Task> existing = taskRepo.findById(id);
        if (existing.isPresent()) {
            Task task = existing.get();
            task.setTitre(titre);
            task.setDescription(description);
            taskRepo.save(task);
        } else {
            throw new RuntimeException("Task non trouvée !");
        }
    }

    @Override
    public void deleteTask(Integer id) {
        if (taskRepo.existsById(id)) {
            taskRepo.deleteById(id);
        } else {
            throw new RuntimeException("Task non trouvée !");
        }
    }

    @Override
    public void setStatut(Integer id, Statut statut) {
        Optional<Task> existing = taskRepo.findById(id);
        if (existing.isPresent()) {
            Task task = existing.get();
            task.setStatut(statut);
            taskRepo.save(task);
        } else {
            throw new RuntimeException("Task non trouvée !");
        }
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    @Override
    public Task getTaskById(Integer id) {
        return taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task non trouvée !"));
    }
}
