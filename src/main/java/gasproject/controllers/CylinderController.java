package gasproject.controllers;

import gasproject.entities.BookingEntity;
import gasproject.entities.CylinderEntity;
import gasproject.service.CylinderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cylinders")
@RequiredArgsConstructor
public  class CylinderController {

    private final CylinderService cylinderService;

    @PostMapping
    public ResponseEntity<?> addCylinder(@RequestParam CylinderEntity.Type type,@RequestParam Double price){
        CylinderEntity saved= cylinderService.addCylinder(type,price);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<CylinderEntity>> getAllCylinders(){
        List<CylinderEntity> list = cylinderService.getAllCylinders();
        return ResponseEntity.ok(list);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<CylinderEntity> deleteCylinder(@PathVariable Long id){
        cylinderService.deleteCylinder(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/status/{status}")
    public ResponseEntity <List<CylinderEntity>> getCylinderByStatus(
            @PathVariable CylinderEntity.Status status) {
        List<CylinderEntity> cylinder =cylinderService.getCylindersByStatus (status) ;
        return ResponseEntity.ok(cylinder);
    }


}
