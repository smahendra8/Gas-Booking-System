package gasproject.repositories;

import gasproject.entities.CylinderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CylinderRepository extends JpaRepository<CylinderEntity,Long> {
    List<CylinderEntity> findByStatus(CylinderEntity.Status status);

    CylinderEntity findByType(CylinderEntity.Type type);




}
