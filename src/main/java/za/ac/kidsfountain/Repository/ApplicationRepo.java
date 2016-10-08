package za.ac.kidsfountain.Repository;

import org.springframework.data.repository.CrudRepository;
import za.ac.kidsfountain.Entity.Application;

/**
 * Created by kidsfountain on 9/6/16.
 */
public interface ApplicationRepo extends CrudRepository<Application,Long> {
}
