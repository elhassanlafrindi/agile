package net.lhm.projagile.Controllers;

import net.lhm.projagile.Services.SprintBacklogService;
import net.lhm.projagile.dto.request.SprintBacklogDTO;
import net.lhm.projagile.dto.response.SprintBacklogResponseDTO;
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

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/sprintbacklogs")
@Tag(name = "Sprint Backlog", description = "Sprint Backlog management APIs")
public class SprintBacklogController {

    private SprintBacklogService sprintBacklogService;

    public SprintBacklogController(SprintBacklogService sprintBacklogService) {
        this.sprintBacklogService = sprintBacklogService;
    }

    @PostMapping
    @Operation(summary = "Create a new Sprint Backlog", description = "Create a new Sprint Backlog with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sprint Backlog created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> createSprintBacklog(@Parameter(description = "Sprint Backlog details") @Valid @RequestBody SprintBacklogDTO sprintBacklogDTO) {
        sprintBacklogService.createSprintBacklog(sprintBacklogDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Sprint Backlog", description = "Update an existing Sprint Backlog with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sprint Backlog updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", 
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Sprint Backlog not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> updateSprintBacklog(@Parameter(description = "Sprint Backlog ID") @PathVariable long id, 
                                                @Parameter(description = "Sprint Backlog details") @Valid @RequestBody SprintBacklogDTO sprintBacklogDTO) {
        sprintBacklogService.updateSprintBacklog(id, sprintBacklogDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Sprint Backlog", description = "Delete a Sprint Backlog by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sprint Backlog deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Sprint Backlog not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> deleteSprintBacklog(@Parameter(description = "Sprint Backlog ID") @PathVariable long id) {
        sprintBacklogService.deleteSprintBacklog(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Get all Sprint Backlogs", description = "Retrieve a list of all Sprint Backlogs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sprint Backlogs retrieved successfully", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = SprintBacklogResponseDTO.class)))
    })
    public ResponseEntity<List<SprintBacklogResponseDTO>> getAllSprintBacklogs() {
        List<SprintBacklogResponseDTO> sprintBacklogs = sprintBacklogService.getAllSprintBacklog();
        return new ResponseEntity<>(sprintBacklogs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Sprint Backlog by ID", description = "Retrieve a Sprint Backlog by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sprint Backlog retrieved successfully", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = SprintBacklogResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Sprint Backlog not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<SprintBacklogResponseDTO> getSprintBacklogById(@Parameter(description = "Sprint Backlog ID") @PathVariable long id) {
        SprintBacklogResponseDTO sprintBacklog = sprintBacklogService.getSprintBacklogById(id);
        return new ResponseEntity<>(sprintBacklog, HttpStatus.OK);
    }

    @PostMapping("/{id}/userstories")
    @Operation(summary = "Add User Stories to Sprint Backlog", description = "Add User Stories to a Sprint Backlog by their IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Stories added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", 
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Sprint Backlog or User Story not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> addUserStoriesToSprintBacklog(@Parameter(description = "Sprint Backlog ID") @PathVariable long id,
                                                             @Parameter(description = "Array of User Story IDs") @RequestBody int[] userStoryIds) {
        sprintBacklogService.addUserStoryToSprintBacklog(id, userStoryIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/userstories")
    @Operation(summary = "Remove User Stories from Sprint Backlog", description = "Remove User Stories from a Sprint Backlog by their IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Stories removed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", 
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Sprint Backlog or User Story not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> removeUserStoriesFromSprintBacklog(@Parameter(description = "Sprint Backlog ID") @PathVariable long id,
                                                                 @Parameter(description = "Array of User Story IDs") @RequestBody int[] userStoryIds) {
        sprintBacklogService.deleteUserStoryFromSprintBacklog(id, userStoryIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/end")
    @Operation(summary = "End Sprint Backlog", description = "End a Sprint Backlog by setting its end date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sprint Backlog ended successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", 
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Sprint Backlog not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> endSprintBacklog(@Parameter(description = "Sprint Backlog ID") @PathVariable long id,
                                                @Parameter(description = "End date") @RequestBody Date date) {
        sprintBacklogService.endOfSprintBacklog(id, date);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}