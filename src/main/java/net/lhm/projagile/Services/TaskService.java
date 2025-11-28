package net.lhm.projagile.Services;

import net.lhm.projagile.dto.request.TaskDTO;
import net.lhm.projagile.dto.response.TaskResponseDTO;
import net.lhm.projagile.entities.Statut;
import net.lhm.projagile.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface TaskService {
    void createTask(TaskDTO taskDTO);
    void updateTask(long id, TaskDTO taskDTO);
    void deleteTask(long id);
    Page<TaskResponseDTO> getAllTask(Pageable pageable);
    TaskResponseDTO getTaskById(long id);
    Page<TaskResponseDTO> getAllTasksCreatedByUser(User user, Pageable pageable);
    Page<TaskResponseDTO> getAllTasksAssignedToUser(User user, Pageable pageable);
    Page<TaskResponseDTO> getAllTasksByStatut(Statut statut, Pageable pageable);
    void updateTaskStatus(long id, Statut statut);
}