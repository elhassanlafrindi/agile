package net.lhm.projagile.Services.impl;

import net.lhm.projagile.Services.TaskService;
import net.lhm.projagile.dto.request.TaskDTO;
import net.lhm.projagile.dto.response.TaskResponseDTO;
import net.lhm.projagile.entities.Task;
import net.lhm.projagile.entities.Statut;
import net.lhm.projagile.entities.User;
import net.lhm.projagile.entities.UserStory;
import net.lhm.projagile.Repositories.TaskRepository;
import net.lhm.projagile.Repositories.UserRepository;
import net.lhm.projagile.Repositories.UserStoryRepository;
import net.lhm.projagile.mapper.TaskMapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private UserStoryRepository userStoryRepository;
    private TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, 
                          UserStoryRepository userStoryRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.userStoryRepository = userStoryRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public void createTask(TaskDTO taskDTO) {
        logger.info("Creating task with title: {}", taskDTO.getTitle());
        Task task = taskMapper.toEntity(taskDTO);

        User createdBy = userRepository.findById(taskDTO.getCreatedByUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + taskDTO.getCreatedByUserId()));
        task.setCreatedBy(createdBy);

        User assignedTo = userRepository.findById(taskDTO.getAssignedToUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + taskDTO.getAssignedToUserId()));
        task.setAssignedTo(assignedTo);

        UserStory userStory = userStoryRepository.findById(taskDTO.getUserStoryId())
                .orElseThrow(() -> new EntityNotFoundException("UserStory not found with id: " + taskDTO.getUserStoryId()));
        task.setUserStory(userStory);
        
        taskRepository.save(task);
        logger.info("Task created successfully with ID: {}", task.getId());
    }

    @Override
    public void updateTask(long id, TaskDTO taskDTO) {
        logger.info("Updating task with ID: {}", id);
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

        existingTask.setTitle(taskDTO.getTitle());
        existingTask.setDescription(taskDTO.getDescription());

        if ( existingTask.getCreatedBy().getId() != taskDTO.getCreatedByUserId()) {
            User createdBy = userRepository.findById(taskDTO.getCreatedByUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + taskDTO.getCreatedByUserId()));
            existingTask.setCreatedBy(createdBy);
        }

        if ( existingTask.getAssignedTo().getId() != taskDTO.getAssignedToUserId()) {
            User assignedTo = userRepository.findById(taskDTO.getAssignedToUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + taskDTO.getAssignedToUserId()));
            existingTask.setAssignedTo(assignedTo);
        }

        if ( existingTask.getUserStory().getId() != taskDTO.getUserStoryId()) {
            UserStory userStory = userStoryRepository.findById(taskDTO.getUserStoryId())
                    .orElseThrow(() -> new EntityNotFoundException("UserStory not found with id: " + taskDTO.getUserStoryId()));
            existingTask.setUserStory(userStory);
        }

        taskRepository.save(existingTask);
        logger.info("Task updated successfully with ID: {}", id);
    }

    @Override
    public void deleteTask(long id) {
        logger.info("Deleting task with ID: {}", id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

        taskRepository.delete(task);
        logger.info("Task deleted successfully with ID: {}", id);
    }

    @Override
    public Page<TaskResponseDTO> getAllTask(Pageable pageable) {
        logger.info("Fetching all tasks with pagination");
        Page<TaskResponseDTO> tasks = taskRepository.findAll(pageable)
                .map(taskMapper::toResponseDTO);
        logger.info("Found {} tasks", tasks.getTotalElements());
        return tasks;
    }

    @Override
    public TaskResponseDTO getTaskById(long id) {
        logger.info("Fetching task by ID: {}", id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
        
        TaskResponseDTO dto = taskMapper.toResponseDTO(task);
        logger.info("Task found with ID: {}", id);
        return dto;
    }

    @Override
    public Page<TaskResponseDTO> getAllTasksCreatedByUser(User user, Pageable pageable) {
        logger.info("Fetching tasks created by user ID: {}", user.getId());
        Page<TaskResponseDTO> tasks = taskRepository.findByCreatedBy(user, pageable)
                .map(taskMapper::toResponseDTO);
        logger.info("Found {} tasks created by user ID: {}", tasks.getTotalElements(), user.getId());
        return tasks;
    }

    @Override
    public Page<TaskResponseDTO> getAllTasksAssignedToUser(User user, Pageable pageable) {
        logger.info("Fetching tasks assigned to user ID: {}", user.getId());
        Page<TaskResponseDTO> tasks = taskRepository.findByAssignedTo(user, pageable)
                .map(taskMapper::toResponseDTO);
        logger.info("Found {} tasks assigned to user ID: {}", tasks.getTotalElements(), user.getId());
        return tasks;
    }

    @Override
    public Page<TaskResponseDTO> getAllTasksByStatut(Statut statut, Pageable pageable) {
        logger.info("Fetching tasks by status: {}", statut);
        Page<TaskResponseDTO> tasks = taskRepository.findByStatut(statut, pageable)
                .map(taskMapper::toResponseDTO);
        logger.info("Found {} tasks with status: {}", tasks.getTotalElements(), statut);
        return tasks;
    }

    @Override
    public void updateTaskStatus(long id, Statut statut) {
        logger.info("Updating status of task with ID: {} to {}", id, statut);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

        task.setStatut(statut);
        taskRepository.save(task);
        logger.info("Task status updated successfully with ID: {}", id);
    }
}