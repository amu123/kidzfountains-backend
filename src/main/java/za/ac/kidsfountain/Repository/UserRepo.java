package za.ac.kidsfountain.Repository;

import org.springframework.data.repository.CrudRepository;
import za.ac.kidsfountain.Entity.User;

/**
 * Created by kidsfountain on 8/12/16.
 */
public interface UserRepo extends CrudRepository<User,Long> {
    User findByStudentNumber(String studentNumber);
    User findByIdNumber(String idNumber);
    User findByEmail(String email);
}
