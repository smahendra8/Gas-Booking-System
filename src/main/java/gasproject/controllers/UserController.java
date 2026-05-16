package gasproject.controllers;

import gasproject.DTO.LoginRequest;
import gasproject.DTO.RegisterRequest;
import gasproject.entities.CylinderEntity;
import gasproject.entities.UserEntity;
import gasproject.service.CylinderService;
import gasproject.service.Userservice;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final Userservice userservice;

    @PostMapping("/register")
    public ResponseEntity<?>register(@RequestBody RegisterRequest registerRequest){
        String savedUser = userservice.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


   @PostMapping("/login")
   public ResponseEntity<String>login(@RequestBody LoginRequest loginRequest){
        if (loginRequest==null){
            return ResponseEntity.badRequest().body("Invalid Mobile Number");
        }
        return ResponseEntity.ok(userservice.login(loginRequest));

   }


    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteUser(@PathVariable Long id){
        userservice.deleteUser(id);
        return ResponseEntity.ok("User Deleted Successfully");
    }

}
