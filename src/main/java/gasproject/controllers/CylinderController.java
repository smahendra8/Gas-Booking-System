package gasproject.controllers;

import gasproject.entities.BookingEntity;
import gasproject.entities.CylinderEntity;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cylinders")
@RequiredArgsConstructor
@Tag(name = " \uD83D\uDD35 Cylinders_Section",description = "\uD83D\uDEE2\uFE0F [Details of Cylinders] ")
public  class CylinderController {

    private final CylinderService cylinderService;


    @Operation(summary = "[Add_Cylinders]",description = "Adding a new Cylinder")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cylinder  Added Sucessfully ",
                    content = @Content(schema = @Schema(implementation = CylinderEntity.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid  Cylinder Details",
                    content =@Content
            )
    })

    @PostMapping
    public ResponseEntity<?> addCylinder(@RequestParam CylinderEntity.Type type,@RequestParam Double price){
        CylinderEntity saved= cylinderService.addCylinder(type,price);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }



    @Operation(summary = "[Get_All_Cylinders]",description = "Getting All  Cylinders")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cylinders List  Showed Sucessfully ",
                    content = @Content(schema = @Schema(implementation = CylinderEntity.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "No  Cylinders found",
                    content =@Content
            )
    })

    @GetMapping
    public ResponseEntity<List<CylinderEntity>> getAllCylinders(){
        List<CylinderEntity> list = cylinderService.getAllCylinders();
        return ResponseEntity.ok(list);
    }


    @Operation(summary = "[Delete_Cylinder]",description = "Deleting Cylinder By Id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cylinder  Deleted Sucessfully ",
                    content = @Content(schema = @Schema(implementation = CylinderEntity.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid  Cylinder Id",
                    content =@Content
            )
    })



    @DeleteMapping("/{id}")
    public ResponseEntity<CylinderEntity> deleteCylinder(@PathVariable Long id){
        cylinderService.deleteCylinder(id);
        return ResponseEntity.noContent().build();
    }



    @Operation(summary = "[Cylinders_By_Status]",description = "Getting Cylinders By Status")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cylinders  Shoed Sucessfully By Status ",
                    content = @Content(schema = @Schema(implementation = CylinderEntity.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid  Status",
                    content =@Content
            )
    })


    @GetMapping("/status/{status}")
    public ResponseEntity <List<CylinderEntity>> getCylinderByStatus(
            @PathVariable CylinderEntity.Status status) {
        List<CylinderEntity> cylinder =cylinderService.getCylindersByStatus (status) ;
        return ResponseEntity.ok(cylinder);
    }


}
