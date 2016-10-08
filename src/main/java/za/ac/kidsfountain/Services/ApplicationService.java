package za.ac.kidsfountain.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.ac.kidsfountain.Entity.Application;
import za.ac.kidsfountain.Repository.ApplicationRepo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kidsfountain on 9/6/16.
 */
@Component
public class ApplicationService {
    @Autowired
    ApplicationRepo repo;

    public Application saveApplicationStatus(String status)
    {
        long id = 1;
        Application app = repo.findOne(id);
        if(app != null){
            app.setApplicationStatus(status);
            return repo.save(app);
        }
        else {
            return repo.save(app);
        }

    }
    public Application createApplication(Application ap)
    {
        return repo.save(ap);
    }
    public List<Application> getAll(){
        /*
        *  Iterator itr = repo.findAll().iterator();
        List<Admin> u = new ArrayList<Admin>();
        while (itr.hasNext()) {
            Admin a = (Admin) itr.next();
            u.add(a);
        }
        return u;*/
        Iterator itr = repo.findAll().iterator();
        List<Application> ls = new ArrayList<Application>();
        while (itr.hasNext())
        {
            Application a = (Application) itr.next();
            ls.add(a);
        }
        return ls;
    }
    public Application setRegistration(String status){
        long id = 1;
        Application app = repo.findOne(id);
        if(app != null){
            app.setRegistrationStatus(status);
            return repo.save(app);
        }
        else {
            return repo.save(app);
        }
    }
    public Application getApplication(long id){
        return repo.findOne(id);
    }

}
