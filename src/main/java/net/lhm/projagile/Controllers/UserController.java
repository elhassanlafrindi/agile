package net.lhm.projagile.Controllers;

import net.lhm.projagile.Services.UserService;
import net.lhm.projagile.dto.request.UserDTO;
import net.lhm.projagile.dto.response.UserResponseDTO;
import jakarta.validation.Valid;
import net.lhm.projagile.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasAuthority;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "User management APIs")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a new User", description = "Create a new User with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", 
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "User with this email already exists", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> createUser(@Parameter(description = "User details") @Valid @RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing User", description = "Update an existing User with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", 
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "User not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> updateUser(@Parameter(description = "User ID") @PathVariable long id, 
                                          @Parameter(description = "User details") @Valid @RequestBody UserDTO userDTO) {
        userService.updateUser(userDTO, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a User", description = "Delete a User by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> deleteUser(@Parameter(description = "User ID") @PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Get all Users", description = "Retrieve a paginated list of all Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = UserResponseDTO.class)))
    })

    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(@Parameter(description = "Pagination information") Pageable pageable) {
        Page<UserResponseDTO> users = userService.getAllUsers(pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('PRODUCT_OWNER')")
    @GetMapping("/{id}")
    @Operation(summary = "Get User by ID", description = "Retrieve a User by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<UserResponseDTO> getUserById(@Parameter(description = "User ID") @PathVariable long id) {
        UserResponseDTO user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping("/{id}/password")
    @Operation(summary = "Change User Password", description = "Change the password of a User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid password data", 
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "User not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> changePassword(@Parameter(description = "User ID") @PathVariable long id,
                                              @Parameter(description = "New password") @RequestBody String newPassword) {
        userService.changePassword(id, newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate User", description = "Activate a User account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User activated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> activateUser(@Parameter(description = "User ID") @PathVariable long id) {
        userService.activateUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/role")
    @Operation(summary = "Change User Role", description = "Change the role of a User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User role changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid role data", 
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "User not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> changeUserRole(@Parameter(description = "User ID") @PathVariable long id,
                                            @Parameter(description = "New role") @RequestBody Role newRole) {
        userService.changeUserRole(id, newRole);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}