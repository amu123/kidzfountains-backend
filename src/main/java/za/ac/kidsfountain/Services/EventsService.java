package za.ac.kidsfountain.Services;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.ac.kidsfountain.Entity.Events;
import za.ac.kidsfountain.Repository.EventsRepo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tlharihani on 9/22/16.
 */
@Component
public class EventsService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(EventsService.class);
    @Autowired
    private EventsRepo repo;

    public Events create(Events e)
    {
        return repo.save(e);
    }
    public Events updateEvent(Events e)
    {

        return repo.save(e);

    }
    public Events getEvent(long id)
    {
        return repo.findOne(id);
    }
    public void deleteEvent(long id)
    {
        repo.delete(id);
    }
    public List<Events> getllEvent(){
        Iterator itr = repo.findAll().iterator();
        List<Events> list = new ArrayList<>();
        while (itr.hasNext()){
            Events e = (Events) itr.next();
            list.add(e);
        }
        return list;
    }

}
