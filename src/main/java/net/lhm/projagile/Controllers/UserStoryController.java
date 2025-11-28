package net.lhm.projagile.Controllers;

import net.lhm.projagile.Services.UserStoryService;
import net.lhm.projagile.dto.request.UserStoryDTO;
import net.lhm.projagile.dto.response.UserStoryResponseDTO;
import net.lhm.projagile.entities.Statut;
import net.lhm.projagile.entities.Priority;
import jakarta.validation.Valid;
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

import java.util.List;

@RestController
@RequestMapping("/api/userstories")
@Tag(name = "User Story", description = "User Story management APIs")
public class UserStoryController {


    private UserStoryService userStoryService;

    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }

    @PostMapping
    @Operation(summary = "Create a new User Story", description = "Create a new User Story with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User Story created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", 
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "ProductBacklog not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> createUserStory(@Parameter(description = "User Story details") @Valid @RequestBody UserStoryDTO userStoryDTO) {
        userStoryService.createUserStory(userStoryDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing User Story", description = "Update an existing User Story with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Story updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", 
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "User Story or ProductBacklog not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> updateUserStory(@Parameter(description = "User Story ID") @PathVariable int id, 
                                                @Parameter(description = "User Story details") @Valid @RequestBody UserStoryDTO userStoryDTO) {
        userStoryService.updateUserStory(id, userStoryDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a User Story", description = "Delete a User Story by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User Story deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User Story not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> deleteUserStory(@Parameter(description = "User Story ID") @PathVariable int id) {
        userStoryService.deleteUserStory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update User Story Status", description = "Update the status of a User Story")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Story status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid status data", 
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "User Story not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> updateUserStoryStatus(@Parameter(description = "User Story ID") @PathVariable int id, 
                                                       @Parameter(description = "Status update details") @Valid @RequestBody Statut statut) {
        userStoryService.updateUserStoryStatut(id, statut);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all User Stories", description = "Retrieve a list of all User Stories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Stories retrieved successfully", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = UserStoryResponseDTO.class)))
    })
    public ResponseEntity<List<UserStoryResponseDTO>> getAllUserStories() {
        List<UserStoryResponseDTO> userStories = userStoryService.getAllUserStory();
        return new ResponseEntity<>(userStories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get User Story by ID", description = "Retrieve a User Story by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Story retrieved successfully", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = UserStoryResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User Story not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<UserStoryResponseDTO> getUserStoryById(@Parameter(description = "User Story ID") @PathVariable int id) {
        UserStoryResponseDTO userStory = userStoryService.getUserStoryById(id);
        return new ResponseEntity<>(userStory, HttpStatus.OK);
    }

    @GetMapping("/status/{statut}")
    @Operation(summary = "Get User Stories by Status", description = "Retrieve a list of User Stories filtered by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Stories retrieved successfully", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = UserStoryResponseDTO.class)))
    })
    public ResponseEntity<List<UserStoryResponseDTO>> getUserStoriesByStatus(@Parameter(description = "User Story status") @PathVariable Statut statut) {
        List<UserStoryResponseDTO> userStories = userStoryService.getAllUserStoryByStatus(statut);
        return new ResponseEntity<>(userStories, HttpStatus.OK);
    }

    @GetMapping("/priority/{priority}")
    @Operation(summary = "Get User Stories by Priority", description = "Retrieve a list of User Stories filtered by priority")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Stories retrieved successfully", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = UserStoryResponseDTO.class)))
    })
    public ResponseEntity<List<UserStoryResponseDTO>> getUserStoriesByPriority(@Parameter(description = "User Story priority") @PathVariable Priority priority) {
        List<UserStoryResponseDTO> userStories = userStoryService.getAllUserStoryByPriority(priority);
        return new ResponseEntity<>(userStories, HttpStatus.OK);
    }
}
