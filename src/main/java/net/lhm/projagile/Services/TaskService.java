package net.lhm.projagile.Services;


import net.lhm.projagile.dto.TaskDTO;
import net.lhm.projagile.dtoResponse.TaskDTORes;
import net.lhm.projagile.entities.Statut;
import net.lhm.projagile.entities.Task;


import java.util.List;

public interface TaskService {
    void createTask(int id,TaskDTO taskDTO);
    Task updateTask(Integer id, TaskDTO taskDTO);
    void deleteTask(Integer id);
    List<TaskDTORes> getAllTasks();
    TaskDTORes getTaskById(Integer id);
    List<TaskDTORes> getByStatus(Statut statut);
    void affecttaskToUtilisateur(int idutilisateur,int idtask);
    List<TaskDTORes> getTasksByUtilisateur(Integer userId);


}
