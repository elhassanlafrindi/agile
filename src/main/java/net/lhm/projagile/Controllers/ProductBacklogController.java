package net.lhm.projagile.Controllers;

import net.lhm.projagile.Services.ProductBacklogService;
import net.lhm.projagile.dto.request.ProductBacklogDTO;
import net.lhm.projagile.dto.response.ProductBacklogResponseDTO;
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
@RequestMapping("/api/productbacklogs")
@Tag(name = "Product Backlog", description = "Product Backlog management APIs")
public class ProductBacklogController {

    private ProductBacklogService productBacklogService;

    public ProductBacklogController(ProductBacklogService productBacklogService) {
        this.productBacklogService = productBacklogService;
    }

    @PostMapping
    @Operation(summary = "Create a new Product Backlog", description = "Create a new Product Backlog with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product Backlog created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> createProductBacklog(@Parameter(description = "Product Backlog details") @Valid @RequestBody ProductBacklogDTO productBacklogDTO) {
        productBacklogService.createProductBacklog(productBacklogDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Product Backlog", description = "Update an existing Product Backlog with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product Backlog updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", 
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Product Backlog not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> updateProductBacklog(@Parameter(description = "Product Backlog ID") @PathVariable long id, 
                                                @Parameter(description = "Product Backlog details") @Valid @RequestBody ProductBacklogDTO productBacklogDTO) {
        productBacklogService.updateProductBacklog(id, productBacklogDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Product Backlog", description = "Delete a Product Backlog by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product Backlog deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product Backlog not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> deleteProductBacklog(@Parameter(description = "Product Backlog ID") @PathVariable long id) {
        productBacklogService.deleteProductBacklog(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Get all Product Backlogs", description = "Retrieve a list of all Product Backlogs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product Backlogs retrieved successfully", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = ProductBacklogResponseDTO.class)))
    })
    public ResponseEntity<List<ProductBacklogResponseDTO>> getAllProductBacklogs() {
        List<ProductBacklogResponseDTO> productBacklogs = productBacklogService.getAllProductBacklog();
        return new ResponseEntity<>(productBacklogs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Product Backlog by ID", description = "Retrieve a Product Backlog by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product Backlog retrieved successfully", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = ProductBacklogResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product Backlog not found", 
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ProductBacklogResponseDTO> getProductBacklogById(@Parameter(description = "Product Backlog ID") @PathVariable long id) {
        ProductBacklogResponseDTO productBacklog = productBacklogService.getProductBacklogById(id);
        return new ResponseEntity<>(productBacklog, HttpStatus.OK);
    }
}