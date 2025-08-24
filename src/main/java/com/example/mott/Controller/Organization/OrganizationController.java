package com.example.mott.Controller.Organization;

import com.example.mott.Dto.Request.OrganizationRequest;
import com.example.mott.Dto.Response.OrganizationResponse;
import com.example.mott.Service.Organization.OrganizationService;
import com.example.mott.Utility.ListResponseStructure;
import com.example.mott.Utility.ResponseBuilder;
import com.example.mott.Utility.ResponseStructure;
import com.example.mott.Utility.SimpleErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/organization")
@Tag(name = "Organization Controller", description = "Collection of API Endpoints for Managing Organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    @Operation(description = "API Endpoint to Create a New Organization",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Organization Created Successfully")
            })
    public ResponseEntity<ResponseStructure<OrganizationResponse>> createOrganization(
            @Valid @RequestBody OrganizationRequest request) {
        OrganizationResponse response = organizationService.createOrganization(request);
        return ResponseBuilder.success(HttpStatus.CREATED, "Organization Created", response);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update")
    @Operation(description = "API Endpoint to Update Existing Organization",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Organization Updated Successfully"),
                    @ApiResponse(responseCode = "404", description = "Organization Not Found", content = {
                            @Content(schema = @Schema(implementation = SimpleErrorResponse.class))
                    })
            })
    public ResponseEntity<ResponseStructure<OrganizationResponse>> updateOrganization(
            @Valid @RequestBody OrganizationRequest request) {
        OrganizationResponse response = organizationService.updateOrganization(request);
        return ResponseBuilder.success(HttpStatus.OK, "Organization Updated Successfully", response);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/{id}")
    @Operation(description = "API Endpoint to Delete an Organization by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Organization Deleted Successfully"),
                    @ApiResponse(responseCode = "404", description = "Organization Not Found", content = {
                            @Content(schema = @Schema(implementation = SimpleErrorResponse.class))
                    })
            })
    public ResponseEntity<ListResponseStructure<Object>> deleteOrganization(@PathVariable UUID id) {
        organizationService.deleteOrganizationById(id);
        return ResponseBuilder.success(HttpStatus.OK, "Organization Deleted Successfully", null);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    @Operation(description = "API Endpoint to Retrieve Organization by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Organization Retrieved Successfully"),
                    @ApiResponse(responseCode = "404", description = "Organization Not Found", content = {
                            @Content(schema = @Schema(implementation = SimpleErrorResponse.class))
                    })
            })
    public ResponseEntity<ResponseStructure<OrganizationResponse>> getOrganizationById(@PathVariable UUID id) {
        OrganizationResponse response = organizationService.getOrganizationById(id);
        return ResponseBuilder.success(HttpStatus.OK, "Organization Retrieved Successfully", response);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/all")
    @Operation(description = "API Endpoint to Retrieve All Organizations for User",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Organizations Retrieved Successfully")
            })
    public ResponseEntity<ListResponseStructure<OrganizationResponse>> getAllOrganizations() {
        List<OrganizationResponse> response = organizationService.getAllOrganizationsForUser();
        return ResponseBuilder.success(HttpStatus.OK, "Organizations fetched successfully", response);
    }
}