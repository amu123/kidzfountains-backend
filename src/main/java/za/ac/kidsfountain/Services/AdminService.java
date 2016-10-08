package za.ac.kidsfountain.Services;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.ac.kidsfountain.Entity.Admin;
import za.ac.kidsfountain.Repository.AdminRepo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kidsfountain on 8/20/16.
 */
@Component
public class AdminService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AdminService.class);
    @Autowired
    AdminRepo repo;

    public Admin createUser(Admin admin) {

        return repo.save(admin);
    }

    public Admin getAdminByStaffNumber(String staffNumber) {
        return repo.findByStaffNumber(staffNumber);
    }
    public Admin getAdminByEmail(String email) {
        return repo.findByEmail(email);
    }
    public Admin getAdminByIdNumber(String id) {
        return repo.findByIdNumber(id);
    }
    public Admin setPassword(String staffNumber, String password) {
            Admin a = getAdminByStaffNumber(staffNumber);
            a.setPassword(password);
            return repo.save(a);

    }
    public Admin clearPassword(String staffNumber) {
        Admin a = getAdminByStaffNumber(staffNumber);
        a.setPassword(null);
        return repo.save(a);

    }
    public List<Admin> getAllAdmin() {
        Iterator itr = repo.findAll().iterator();
        List<Admin> u = new ArrayList<Admin>();
        while (itr.hasNext()) {
            Admin a = (Admin) itr.next();
            u.add(a);
        }
        return u;
    }
    public void deleteAdmin(Admin admin) {
       Admin admin1 =  repo.findByStaffNumber(admin.getStaffNumber());
        repo.delete(admin1);
    }
    public Admin updateAdmin(Admin a)
    {
        return repo.save(a);
    }
}
