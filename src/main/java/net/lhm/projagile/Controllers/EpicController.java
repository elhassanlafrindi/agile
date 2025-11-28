package net.lhm.projagile.Controllers;

import net.lhm.projagile.Services.EpicService;
import net.lhm.projagile.dto.request.EpicDTO;
import net.lhm.projagile.dto.response.EpicResponseDTO;
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
@RequestMapping("/api/epics")
@Tag(name = "Epic", description = "Epic management APIs")
public class EpicController {

    private EpicService epicService;

    public EpicController(EpicService epicService) {
        this.epicService = epicService;
    }

    @PostMapping
    @Operation(summary = "Create a new Epic", description = "Create a new Epic with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Epic created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> createEpic(@Parameter(description = "Epic details") @Valid @RequestBody EpicDTO epicDTO) {
        epicService.createEpic(epicDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Epic", description = "Update an existing Epic with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Epic updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", 
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Epic not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> updateEpic(@Parameter(description = "Epic ID") @PathVariable long id, 
                                          @Parameter(description = "Epic details") @Valid @RequestBody EpicDTO epicDTO) {
        epicService.updateEpic(id, epicDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an Epic", description = "Delete an Epic by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Epic deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Epic not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> deleteEpic(@Parameter(description = "Epic ID") @PathVariable long id) {
        epicService.deleteEpic(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Get all Epics", description = "Retrieve a paginated list of all Epics")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Epics retrieved successfully", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = EpicResponseDTO.class)))
    })
    public ResponseEntity<Page<EpicResponseDTO>> getAllEpics(@Parameter(description = "Pagination information") Pageable pageable) {
        Page<EpicResponseDTO> epics = epicService.getAllEpics(pageable);
        return new ResponseEntity<>(epics, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Epic by ID", description = "Retrieve an Epic by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Epic retrieved successfully", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = EpicResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Epic not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<EpicResponseDTO> getEpicById(@Parameter(description = "Epic ID") @PathVariable long id) {
        EpicResponseDTO epic = epicService.getEpicById(id);
        return new ResponseEntity<>(epic, HttpStatus.OK);
    }

    @PostMapping("/{id}/userstories")
    @Operation(summary = "Add User Stories to Epic", description = "Add User Stories to an Epic by their IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Stories added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", 
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Epic or User Story not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> addUserStoriesToEpic(@Parameter(description = "Epic ID") @PathVariable long id,
                                                    @Parameter(description = "Array of User Story IDs") @RequestBody long[] userStoryIds) {
        epicService.addUserStoryToEpic(id, userStoryIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/userstories")
    @Operation(summary = "Remove User Stories from Epic", description = "Remove User Stories from an Epic by their IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Stories removed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", 
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Epic or User Story not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> removeUserStoriesFromEpic(@Parameter(description = "Epic ID") @PathVariable long id,
                                                         @Parameter(description = "Array of User Story IDs") @RequestBody long[] userStoryIds) {
        epicService.deleteUserStoryFromEpic(id, userStoryIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}