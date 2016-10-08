package za.ac.kidsfountain.Repository;

import org.springframework.data.repository.CrudRepository;
import za.ac.kidsfountain.Entity.Admin;

/**
 * Created by kidsfountain on 8/20/16.
 */
public interface AdminRepo extends CrudRepository<Admin,Long> {
    Admin findByStaffNumber(String staffNumber);
    Admin findByEmail(String email);
    Admin findByIdNumber(String email);
    Admin findByStaffNumberAndPassword(String staffNumber,String password);


}
