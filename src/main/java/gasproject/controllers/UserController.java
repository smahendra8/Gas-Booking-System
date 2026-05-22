package gasproject.controllers;

import gasproject.DTO.LoginRequest;
import gasproject.DTO.RegisterRequest;
import gasproject.entities.CylinderEntity;
import gasproject.entities.UserEntity;
import gasproject.service.CylinderService;
import gasproject.service.Userservice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "\uD83D\uDC64 Accounts_Section",description = "\uD83E\uDDD1 [Details of Accounts] ")
public class UserController {

    private final Userservice userservice;

    @Operation(summary = "[User_Register]",description = "New User Register")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User Sucessfully Registered",
                    content = @Content(schema = @Schema(implementation = Userservice.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid Details",
                    content =@Content
            )
    })


    @PostMapping("/register")
    public ResponseEntity<?>register(@RequestBody RegisterRequest registerRequest){
        String savedUser = userservice.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }



    @Operation(summary = "[User_Login]",description = "UserLogin with mobile and passwrd")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User Sucessfully Logined",
                    content = @Content(schema = @Schema(implementation = Userservice.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid Details  to Login",
                    content =@Content
            )
    })


    @PostMapping("/login")
   public ResponseEntity<String>login(@RequestBody LoginRequest loginRequest){
        if (loginRequest==null){
            return ResponseEntity.badRequest().body("Invalid Mobile Number");
        }
        return ResponseEntity.ok(userservice.login(loginRequest));

   }



    @Operation(summary = "[Delete_User]",description = "Deleting the User")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User Sucessfully Deleted",
                    content = @Content(schema = @Schema(implementation = Userservice.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid Details  to Delete",
                    content =@Content
            )
    })


    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteUser(@PathVariable Long id){
        userservice.deleteUser(id);
        return ResponseEntity.ok("User Deleted Successfully");
    }

}
