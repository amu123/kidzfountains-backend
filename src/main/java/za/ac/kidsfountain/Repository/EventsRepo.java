package za.ac.kidsfountain.Repository;

import org.springframework.data.repository.CrudRepository;
import za.ac.kidsfountain.Entity.Events;

/**
 * Created by tlharihani on 9/22/16.
 */
public interface EventsRepo extends CrudRepository<Events,Long> {
}
