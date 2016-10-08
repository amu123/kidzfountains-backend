package za.ac.kidsfountain.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.ac.kidsfountain.Entity.SuperAdmin;
import za.ac.kidsfountain.Repository.SuperAdminRepo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kidsfountain on 9/8/16.
 */
@Component
public class SuperAdminService {
    @Autowired
    SuperAdminRepo repo;

    public SuperAdmin createAdim(SuperAdmin adim)
    {
        return repo.save(adim);
    }
    public SuperAdmin getSuperAdmin(long adminNumber)
    {
        return repo.findByAdminNumber(adminNumber);
    }
    public List<SuperAdmin> getSuperAdmins()
    {
        Iterator itr =   repo.findAll().iterator();
        List<SuperAdmin> ls = new ArrayList<SuperAdmin>();
        while (itr.hasNext()) {
            SuperAdmin s =(SuperAdmin) itr.next();
            ls.add(s);
        }
        return ls;

    }
}
