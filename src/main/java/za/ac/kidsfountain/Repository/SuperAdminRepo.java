package za.ac.kidsfountain.Repository;

import org.springframework.data.repository.CrudRepository;
import za.ac.kidsfountain.Entity.SuperAdmin;

/**
 * Created by kidsfountain on 9/8/16.
 */
public interface SuperAdminRepo extends CrudRepository<SuperAdmin,Long> {
    SuperAdmin findByAdminNumber(long adminNumber);
}
