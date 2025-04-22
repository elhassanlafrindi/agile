package net.lhm.projagile.Services;

import jakarta.transaction.Transactional;
import net.lhm.projagile.Repositories.TaskRepo;
import net.lhm.projagile.Repositories.UserStoryRepo;
import net.lhm.projagile.Repositories.UtilisateurRepo;
import net.lhm.projagile.dto.SprintBacklogDTO;
import net.lhm.projagile.dto.TaskDTO;
import net.lhm.projagile.dto.UserStoryDTO;
import net.lhm.projagile.dto.UtilisateurDTO;
import net.lhm.projagile.dtoResponse.TaskDTORes;
import net.lhm.projagile.entities.Statut;
import net.lhm.projagile.entities.Task;
import net.lhm.projagile.entities.UserStory;
import net.lhm.projagile.entities.Utilisateur;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService{
    private final TaskRepo taskRepo;
    private final UserStoryRepo userStoryRepo;
    private final UtilisateurRepo utilisateurRepo;
    public TaskServiceImpl(TaskRepo taskRepo, UserStoryRepo userStoryRepo, UtilisateurRepo utilisateurRepo) {
        this.taskRepo = taskRepo;
        this.userStoryRepo = userStoryRepo;
        this.utilisateurRepo = utilisateurRepo;
    }

    @Override
    @Transactional
    public void createTask(int id,TaskDTO taskDTO) {
        Optional<UserStory> userStory=userStoryRepo.findById(id);
        if(userStory.isPresent()){
             taskRepo.save(Task.builder()
                    .titre(taskDTO.getTitre())
                    .description(taskDTO.getDescription())
                    .statut(taskDTO.getStatut())
                      .userStory(userStory.get())
                    .build());
        }

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
    public List<TaskDTORes> getAllTasks() {
            List<Task> tasks=taskRepo.findAll();
        return tasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTORes getTaskById(Integer id) {
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée !"));
        return convertToDTO(task);
    }

    @Override
    public List<TaskDTORes> getByStatus(Statut statut) {
        List<Task> tasks = taskRepo.findByStatut(statut);
        return tasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void affecttaskToUtilisateur(int idtask,int idutilisateur) {
        Utilisateur utilisateur=utilisateurRepo.findById(idutilisateur).
                orElseThrow(()->new RuntimeException("Utilisateur not found!"));
        Task task=taskRepo.findById(idtask).
                orElseThrow(() -> new RuntimeException("Task not found!"));
        task.setUser(utilisateur);
        taskRepo.save(task);
    }

    @Override
    public List<TaskDTORes> getTasksByUtilisateur(Integer userId) {
        Optional<Utilisateur> user=utilisateurRepo.findById(userId);
        if (user.isPresent()) {
            List<Task> tasks = taskRepo.findByUser(user.get());
            return tasks.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return null;
    }

    private TaskDTORes convertToDTO(Task task) {
        TaskDTORes dto = new TaskDTORes();
        dto.setId(task.getId());
        dto.setTitre(task.getTitre());
        dto.setDescription(task.getDescription());
        dto.setStatut(task.getStatut());

        if (task.getSprintBacklog() != null) {
            SprintBacklogDTO sprintDTO = new SprintBacklogDTO();
            sprintDTO.setId(task.getSprintBacklog().getId());
            sprintDTO.setTitle(task.getSprintBacklog().getTitle());
            dto.setSprintBacklog(sprintDTO);
        }

        if (task.getUser() != null) {
            UtilisateurDTO userDTO = new UtilisateurDTO();
            userDTO.setNom(task.getUser().getNom());
            userDTO.setPrenom(task.getUser().getPrenom());
            userDTO.setPassword(task.getUser().getPassword());
            userDTO.setEmail(task.getUser().getEmail());
            userDTO.setRole(task.getUser().getRole());
            dto.setUser(userDTO);
        }

        if (task.getUserStory() != null) {
            UserStoryDTO userStoryDTO = new UserStoryDTO();
            userStoryDTO.setId(task.getUserStory().getId());
            userStoryDTO.setTitle(task.getUserStory().getTitle());
            userStoryDTO.setDescription(task.getUserStory().getDescription());
            userStoryDTO.setPriorite(task.getUserStory().getPriorite());
            userStoryDTO.setStatut(task.getUserStory().getStatut());
            dto.setUserStory(userStoryDTO);
        }

        return dto;
    }

}
