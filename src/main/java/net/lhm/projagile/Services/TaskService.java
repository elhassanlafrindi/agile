package net.lhm.projagile.Services;


import net.lhm.projagile.dto.TaskDTO;
import net.lhm.projagile.entities.Statut;
import net.lhm.projagile.entities.Task;


import java.util.List;

public interface TaskService {
    Task createTask(TaskDTO taskDTO);
    Task updateTask(Integer id, TaskDTO taskDTO);
    void deleteTask(Integer id);
    List<Task> getAllTasks();
    Task getTaskById(Integer id);
    List<Task> getByStatus(Statut statut);
    void affecttaskToUtilisateur(int idutilisateur,int idtask);
}
