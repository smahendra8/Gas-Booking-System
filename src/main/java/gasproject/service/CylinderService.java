package gasproject.service;

import gasproject.entities.CylinderEntity;
import gasproject.repositories.CylinderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CylinderService {

    private final CylinderRepository cylinderRepository;

    public CylinderEntity addCylinder(CylinderEntity.Type type,Double price){
        CylinderEntity cylinder = new CylinderEntity();
        cylinder.setType(type);
        cylinder.setStatus(cylinder.getStatus());
        cylinder.setPrice(price);



        return cylinderRepository.save(cylinder);
    }

    public List<CylinderEntity>getAllCylinders(){
        return cylinderRepository.findAll();
    }


    public void deleteCylinder(Long id){

        cylinderRepository.deleteById(id);
    }

    public List<CylinderEntity> getCylindersByStatus(CylinderEntity.Status status) {
        return cylinderRepository.findByStatus(status);
    }




}
