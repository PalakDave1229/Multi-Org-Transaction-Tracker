package com.example.mott.Controller.User;

import com.example.mott.Dto.Request.UserRequest;
import com.example.mott.Dto.Response.UserResponse;
import com.example.mott.Service.User.UserService;
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
@RequestMapping("/")
@Tag(name = "User Controller", description = "Collection of API Endpoints Dealing with User Data")
public class UserController {

    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("user")
    @Operation(description = "API Endpoint to Create a New User",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User Created Successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid Request", content = {
                            @Content(schema = @Schema(implementation = SimpleErrorResponse.class))
                    })
            })
    public ResponseEntity<ResponseStructure<UserResponse>> createUser(
            @Valid @RequestBody UserRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseBuilder.success(HttpStatus.CREATED, "User Created", response);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("user/update")
    @Operation(description = "API Endpoint to Update Existing User",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User Updated Successfully"),
                    @ApiResponse(responseCode = "404", description = "User Not Found", content = {
                            @Content(schema = @Schema(implementation = SimpleErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = {
                            @Content(schema = @Schema(implementation = SimpleErrorResponse.class))
                    })
            })
    public ResponseEntity<ResponseStructure<UserResponse>> updateUser(@Valid @RequestBody UserRequest request) {
        UserResponse response = userService.updateUser(request);
        return ResponseBuilder.success(HttpStatus.OK, "User Updated Successfully", response);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("user/delete/{id}")
    @Operation(description = "API Endpoint to Delete a User By ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User Deleted Successfully"),
                    @ApiResponse(responseCode = "404", description = "User Not Found", content = {
                            @Content(schema = @Schema(implementation = SimpleErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = {
                            @Content(schema = @Schema(implementation = SimpleErrorResponse.class))
                    })
            })
    public ResponseEntity<ListResponseStructure<Object>> deleteUser(@PathVariable UUID id) {
        userService.deleteUserById(id);
        return ResponseBuilder.success(HttpStatus.OK, "User Deleted Successfully", null);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("user/{id}")
    @Operation(description = "API Endpoint to Retrieve User by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User Found Successfully"),
                    @ApiResponse(responseCode = "404", description = "User Not Found", content = {
                            @Content(schema = @Schema(implementation = SimpleErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = {
                            @Content(schema = @Schema(implementation = SimpleErrorResponse.class))
                    })
            })
    public ResponseEntity<ResponseStructure<UserResponse>> getUserById(@PathVariable UUID id) {
        UserResponse response = userService.getUserById(id);
        return ResponseBuilder.success(HttpStatus.OK, "User Retrieved Successfully", response);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("user/all")
    @Operation(description = "API Endpoint to Retrieve All Users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Users Retrieved Successfully")
            })
    public ResponseEntity<ListResponseStructure<UserResponse>> getAllUsers() {
        List<UserResponse> response = userService.getAllUsers();
        return ResponseBuilder.success(HttpStatus.OK, "Users fetched successfully", response);
    }
}