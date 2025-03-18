package net.lhm.projagile.Services;

import net.lhm.projagile.entities.Task;


import java.util.List;

public interface TaskService {
    Task addTask(Integer id, String titre, String description);
    void updateTask(Integer id, String titre, String description); // Ajout de l'ID
    void deleteTask(Integer id); // Ajout de l'ID
    void setStatut(Integer id, Task.Status statut); // Correction de UserStory.Status -> Task.Status
    List<Task> getAllTasks();
    Task getTaskById(Integer id);
}
