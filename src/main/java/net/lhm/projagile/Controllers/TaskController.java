package net.lhm.projagile.Controllers;

import net.lhm.projagile.Services.TaskService;
import net.lhm.projagile.dto.request.TaskDTO;
import net.lhm.projagile.dto.response.TaskResponseDTO;
import net.lhm.projagile.entities.Statut;
import net.lhm.projagile.entities.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task", description = "Task management APIs")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @Operation(summary = "Create a new Task", description = "Create a new Task with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> createTask(@Parameter(description = "Task details") @Valid @RequestBody TaskDTO taskDTO) {
        taskService.createTask(taskDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Task", description = "Update an existing Task with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", 
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Task not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> updateTask(@Parameter(description = "Task ID") @PathVariable long id, 
                                          @Parameter(description = "Task details") @Valid @RequestBody TaskDTO taskDTO) {
        taskService.updateTask(id, taskDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Task", description = "Delete a Task by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> deleteTask(@Parameter(description = "Task ID") @PathVariable long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Get all Tasks", description = "Retrieve a paginated list of all Tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = TaskResponseDTO.class)))
    })
    public ResponseEntity<Page<TaskResponseDTO>> getAllTasks(@Parameter(description = "Pagination information") Pageable pageable) {
        Page<TaskResponseDTO> tasks = taskService.getAllTask(pageable);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Task by ID", description = "Retrieve a Task by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task retrieved successfully", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = TaskResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Task not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<TaskResponseDTO> getTaskById(@Parameter(description = "Task ID") @PathVariable long id) {
        TaskResponseDTO task = taskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/created-by/{userId}")
    @Operation(summary = "Get Tasks created by User", description = "Retrieve a paginated list of Tasks created by a specific User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = TaskResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Page<TaskResponseDTO>> getTasksCreatedByUser(@Parameter(description = "User ID") @PathVariable long userId,
                                                                     @Parameter(description = "Pagination information") Pageable pageable) {
        // In a real implementation, you would fetch the user from a repository
        User user = new User();
        user.setId( userId);
        Page<TaskResponseDTO> tasks = taskService.getAllTasksCreatedByUser(user, pageable);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/assigned-to/{userId}")
    @Operation(summary = "Get Tasks assigned to User", description = "Retrieve a paginated list of Tasks assigned to a specific User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = TaskResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Page<TaskResponseDTO>> getTasksAssignedToUser(@Parameter(description = "User ID") @PathVariable long userId,
                                                                      @Parameter(description = "Pagination information") Pageable pageable) {

        User user = new User();
        user.setId( userId);
        Page<TaskResponseDTO> tasks = taskService.getAllTasksAssignedToUser(user, pageable);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/status/{statut}")
    @Operation(summary = "Get Tasks by Status", description = "Retrieve a paginated list of Tasks filtered by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = TaskResponseDTO.class)))
    })
    public ResponseEntity<Page<TaskResponseDTO>> getTasksByStatus(@Parameter(description = "Task status") @PathVariable Statut statut,
                                                                @Parameter(description = "Pagination information") Pageable pageable) {
        Page<TaskResponseDTO> tasks = taskService.getAllTasksByStatut(statut, pageable);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update Task Status", description = "Update the status of a Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid status data", 
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Task not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> updateTaskStatus(@Parameter(description = "Task ID") @PathVariable long id,
                                               @Parameter(description = "Status update details") @Valid @RequestBody Statut statut) {
        taskService.updateTaskStatus(id, statut);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}