package net.lhm.projagile.Services;


import net.lhm.projagile.entities.Statut;
import net.lhm.projagile.entities.Task;


import java.util.List;

public interface TaskService {
    Task addTask(Integer id, String titre, String description);
    void updateTask(Integer id, String titre, String description);
    void deleteTask(Integer id);
    void setStatut(Integer id, Statut statut);
    List<Task> getAllTasks();
    Task getTaskById(Integer id);
}
