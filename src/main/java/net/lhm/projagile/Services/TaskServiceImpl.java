package net.lhm.projagile.Services;

import jakarta.transaction.Transactional;
import net.lhm.projagile.Repositories.TaskRepo;
import net.lhm.projagile.dto.TaskDTO;
import net.lhm.projagile.entities.Statut;
import net.lhm.projagile.entities.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService{
    private final TaskRepo taskRepo;

    public TaskServiceImpl(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    @Override
    @Transactional
    public Task createTask(TaskDTO taskDTO) {
        return taskRepo.save(Task.builder()
                .titre(taskDTO.getTitre())
                .description(taskDTO.getDescription())
                .statut(taskDTO.getStatut())
                .build());
    }

    @Override
    public Task updateTask(Integer id, TaskDTO taskDTO) {
        Optional<Task> taskOptional = taskRepo.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setTitre(taskDTO.getTitre());
            task.setDescription(taskDTO.getDescription());
            task.setStatut(taskDTO.getStatut());

            return taskRepo.save(task);
        }
        throw new RuntimeException("Tâche non trouvée !");
    }

    @Override
    public void deleteTask(Integer id) {
        if (!taskRepo.existsById(id)) {
            throw new RuntimeException("Tâche non trouvée !");
        }
        taskRepo.deleteById(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    @Override
    public Task getTaskById(Integer id) {
        return taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée !"));
    }

    @Override
    public List<Task> getByStatus(Statut statut) {
        return taskRepo.findByStatut(statut);
    }
}
