package za.ac.kidsfountain.Services;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.ac.kidsfountain.Entity.User;
import za.ac.kidsfountain.Repository.UserRepo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kidsfountain on 8/12/16.
 */
@Component
public class UserService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepo userRepo;

    public User createUser(User user)
    {
        return userRepo.save(user);
    }
    public List<User> getAllUsers() {
        Iterator itr = userRepo.findAll().iterator();
        List<User> u = new ArrayList<User>();
        while (itr.hasNext()) {
            User a = (User) itr.next();
            u.add(a);
        }
        return u;
    }
    public User getUserByStudentNumber(String studentNumber) {
        return userRepo.findByStudentNumber(studentNumber);
    }
    public User getAdminByEmail(String email) {
        return userRepo.findByEmail(email);
    }
    public User updateUser(User user) {
            return userRepo.save(user);
    }
    public User updateStatus(String studentNumber,String status) {
    User u = getUserByStudentNumber(studentNumber);
    u.setApplicationStatus(status);
    return userRepo.save(u);
}
    public User getUserByID(long id)
    {
        return userRepo.findOne(id);
    }
    public User setPassword(String studentNumber,String password){
        User u = getUserByStudentNumber(studentNumber);
        u.setPassword(password);
        return userRepo.save(u);
    }
    public User clearPassword(String staffNumber) {
        User a = getUserByStudentNumber(staffNumber);
        a.setPassword(null);
        return userRepo.save(a);

    }
    public void deleteUser(User user) {
        User user1 = getUserByStudentNumber(user.getStudentNumber());
        userRepo.delete(user1);
    }
    public User getStudentByIdNumber(String id)
    {
        return userRepo.findByIdNumber(id);
    }

}
