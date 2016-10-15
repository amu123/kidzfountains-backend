//package za.ac.kidsfountain.Controllers;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import za.ac.kidsfountain.Entity.User;
//import za.ac.kidsfountain.Services.UserService;
//import za.ac.kidsfountain.utils.Common;
//import za.ac.kidsfountain.utils.EncrypPassword;
//import za.ac.kidsfountain.utils.SendEmail;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Random;
//
///**
// * Created by kidsfountain on 8/12/16.
// */
//@RestController
//@RequestMapping("/users")
//public class UserController {
//    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
//    ObjectMapper objectMapper = new Common().setUpObjectMapper();
//
//
//    @Autowired
//    UserService service;
//
//    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//
//    public UserController(){
//        headers.add("Content-Type", "application/json");
//        headers.add("Accept", "application/json");
//    }
//
//    @RequestMapping(value = "", method = RequestMethod.PUT)
//    public ResponseEntity createUser(@RequestBody String json)
//    {
//        try {
//            final JsonNode data = objectMapper.readTree(json);
//
//            User user = new User(data.findValue("name").asText(),
//                    data.findValue("surname").asText(),
//                    data.findValue("idNumber").asText(),
//                    data.findValue("parentName").asText(),
//                    data.findValue("parentSurname").asText(),
//                    data.findValue("address").asText(),
//                    data.findValue("contact").asText(),
//                    data.findValue("parentIdNumber").asText(),
//                    data.findValue("email").asText());
//            boolean computerLessons = false;
//            boolean swimmingLessons = false;
//            boolean soccerLessons = false;
//            boolean dancingLessons = false;
//            if(data.has("dacingLessons"))
//            {
//                dancingLessons = data.findValue("dacingLessons").asBoolean();
//
//            }
//            if(data.has("computerLessons"))
//            {
//                computerLessons = data.findValue("computerLessons").asBoolean();
//            }
//            if(data.has("swimmingLessons"))
//            {
//                swimmingLessons = data.findValue("swimmingLessons").asBoolean();
//
//            }
//            if(data.has("soccerLessons"))
//            {
//                soccerLessons = data.findValue("soccerLessons").asBoolean();
//            }
//            user.setComputerLessons(computerLessons);
//            user.setDacingLessons(dancingLessons);
//            user.setSwimmingLessons(swimmingLessons);
//            user.setSoccerLessons(soccerLessons);
//
//            Random random = new Random();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
//            Date today = new Date();
//            int year = today.getYear();
//            System.out.println(year + " --------------");
//
//
//            String studentNumber = "2017" + random.nextInt(10000);
//            boolean valid = false;
//            if(service.getUserByStudentNumber(studentNumber) == null && studentNumber.length() == 8)
//            {
//                valid =true;
//            }
//            while (!valid)
//            {
//                studentNumber = "2017" + random.nextInt(10000);
//                if(service.getUserByStudentNumber(studentNumber) == null && studentNumber.length() == 8)
//                {
//                    valid =true;
//                }
//            }
//            if(service.getStudentByIdNumber(user.getIdNumber()) == null)
//            {
//                user.setStudentNumber(studentNumber);
//                user.setApplicationStatus("Application received, waiting for admin approval");
//                User user1 = service.createUser(user);
//                String email = user1.getEmail();
//                String subject = "Application";
//                String body = "Hi " + user1.getName() + "\n\nYour application has successfully received, just wait for admin approval \n\n\nYour student number is: "+ user1.getStudentNumber() +
//                        " use it to sign in\n\n\n\n\nWarmest regards\nKids fountain";
//                SendEmail sendEmail = new SendEmail();
//                String mess = sendEmail.sendEmail(email, subject, body);
//                    return new ResponseEntity(user1,HttpStatus.CREATED);
//
//            }
//            else
//            {
//                //String message = "Student id number already exist...";
//                user.setApplicationStatus("Student id number already exist...");
//
//                return new ResponseEntity(user,HttpStatus.OK);
//            }
//
//
//        }
//        catch (Exception e){
//            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
//        }
//
//    }
//    @RequestMapping(value = "",method = RequestMethod.GET)
//    public ResponseEntity getAllUsers()
//    {
//        try
//        {
//            return new ResponseEntity<>(service.getAllUsers(),HttpStatus.OK);
//        }
//        catch (Exception e)
//        {
//            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
//    @RequestMapping(value = "/approve", method = RequestMethod.POST)
//    public ResponseEntity approve(@RequestBody String user)
//    {
//        try
//        {
//            JsonNode data = objectMapper.readTree(user);
//            User u = service.getUserByStudentNumber(data.findValue("studentNumber").asText());
//            if(u != null)
//            {
//                User user1 = service.updateStatus(u.getStudentNumber(),"Approved");
//
//                String email = user1.getEmail();
//                String subject = "Application Status";
//                String body = "Hi " + user1.getName() + "\nstudent number: " + user1.getStudentNumber() + "\n\nYour application has Approved, you be alerted on registration procedure" +
//                        "\n\nWarmest regards\nKidz fountains";
//
//                SendEmail sendEmail = new SendEmail();
//                sendEmail.sendEmail(email, subject, body);
//                return new ResponseEntity(user1,HttpStatus.OK);
//            }
//            else
//            {
//                String message = "user not found";
//
//                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
//            }
//
//
//        }
//        catch (Exception e)
//        {
//            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
//    @RequestMapping(value = "/deregister", method = RequestMethod.POST)
//    public ResponseEntity deRegister(@RequestBody String user)
//    {
//        try
//        {
//            JsonNode data = objectMapper.readTree(user);
//            User u = service.getUserByStudentNumber(data.findValue("studentNumber").asText());
//            if(u != null)
//            {
//                User user1 = service.updateStatus(u.getStudentNumber(),"You are de-activated from the system, Consult the admin.....");
//                String email = user1.getEmail();
//                String subject = "Registration de-activation";
//                String body = "Hi " + user1.getName() + "\nStudent Number: "+ user1.getStudentNumber()+"\n\nYou have been de-activated from the system...for more details please consult the admins"
//                        + "\n\nWarmest regards\nKidz fountains";
//
//                SendEmail sendEmail = new SendEmail();
//                sendEmail.sendEmail(email, subject, body);
//
//                return new ResponseEntity(user1,HttpStatus.OK);
//            }
//            else
//            {
//                String message = "user not found";
//
//                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
//            }
//
//
//        }
//        catch (Exception e)
//        {
//            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
//    @RequestMapping(value = "/decline", method = RequestMethod.POST)
//    public ResponseEntity decline(@RequestBody String user)
//    {
//        try
//        {
//            JsonNode data = objectMapper.readTree(user);
//            User u = service.getUserByStudentNumber(data.findValue("studentNumber").asText());
//            if(u != null)
//            {
//                User user1 = service.updateStatus(u.getStudentNumber(),"Declined");
//                String email = user1.getEmail();
//                String subject = "Application status";
//                String body = "Hi " + user1.getName() + "\nStudent Number: "+ user1.getStudentNumber()+"\n\nYour application has declined, please consult the admins for more info"
//                        + "\n\nWarmest regards\nKidz fountains";
//                SendEmail sendEmail = new SendEmail();
//                sendEmail.sendEmail(email, subject, body);
//                return new ResponseEntity(user1,HttpStatus.OK);
//            }
//            else
//            {
//                String message = "user not found";
//                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
//            }
//
//
//        }
//        catch (Exception e)
//        {
//            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public ResponseEntity register(@RequestBody String user)
//    {
//        try
//        {
//            JsonNode data = objectMapper.readTree(user);
//            User u = service.getUserByStudentNumber(data.findValue("studentNumber").asText());
//
//            if(u != null)
//            {
//                if(u.getApplicationStatus().equalsIgnoreCase("Registered"))
//                {
//                    String message = "Already registered";
//                    return new ResponseEntity(message,HttpStatus.OK);
//                }
//                else
//                {
//                    User user1 = service.updateStatus(u.getStudentNumber(),"Registered");
//                    return new ResponseEntity(user1,HttpStatus.OK);
//                }
//
//
//            }
//            else
//            {
//                String message = "user not found";
//                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
//            }
//
//
//        }
//        catch (Exception e)
//        {
//            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//    @RequestMapping(value = "/user", method = RequestMethod.POST)
//    public ResponseEntity getSingleUser(@RequestBody String user)
//    {
//        try
//        {
//            JsonNode data = objectMapper.readTree(user);
//            User u = service.getUserByStudentNumber(data.findValue("studentNumber").asText());
//            if(u != null)
//            {
//                return new ResponseEntity(u,HttpStatus.OK);
//            }
//            else
//            {
//                String message = "user not found";
//                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
//            }
//
//
//        }
//        catch (Exception e)
//        {
//            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @RequestMapping(value = "/user/idNumber", method = RequestMethod.POST)
//    public ResponseEntity getSingleUserById(@RequestBody String user)
//    {
//        try
//        {
//            JsonNode data = objectMapper.readTree(user);
//            User u = service.getStudentByIdNumber(data.findValue("idNumber").asText());
//            if(u != null)
//            {
//                return new ResponseEntity(u,HttpStatus.OK);
//            }
//            else
//            {
//                String message = "user not found";
//                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
//            }
//
//
//        }
//        catch (Exception e)
//        {
//            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @RequestMapping(value = "/setpassword",method = RequestMethod.POST)
//    public ResponseEntity setPassword(@RequestBody String json)
//    {
//        try {
//            JsonNode data = objectMapper.readTree(json);
//            User user = service.getUserByStudentNumber(data.findValue("studentNumber").asText());
//            if(user != null)
//            {
//                EncrypPassword encrypPassword = new EncrypPassword();
//                encrypPassword.setPassword(data.findValue("password").asText());
//                String ep = encrypPassword.getPassword();
//
//
//                User user1 = service.setPassword(user.getStudentNumber(),ep);
//                String email = user1.getEmail();
//                String subject = "Password confirmation";
//                String body = "Hi " + user1.getName() + "\nStudent Number: "+ user1.getStudentNumber()+"\n\nYour have successfully set your password nad your password is" + user.getPassword()
//                        + "\n\nWarmest regards\nKids fountain";
//                SendEmail sendEmail = new SendEmail();
//                sendEmail.sendEmail(email, subject, body);
//                return new ResponseEntity(user1,HttpStatus.OK);
//            }
//            else {
//                return new ResponseEntity(HttpStatus.NOT_FOUND);
//            }
//
//        }
//        catch (Exception e)
//        {
//            String message = e.getMessage();
//            return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
//        }
//    }
//    @RequestMapping(value = "/clearpassword",method = RequestMethod.POST)
//    public ResponseEntity clearPassword(@RequestBody String json)
//    {
//        try {
//            JsonNode data = objectMapper.readTree(json);
//            User user = service.getUserByStudentNumber(data.findValue("studentNumber").asText());
//            if(user != null)
//            {
//                return new ResponseEntity(service.clearPassword(user.getStudentNumber()),HttpStatus.OK);
//            }
//            else {
//                return new ResponseEntity(HttpStatus.NOT_FOUND);
//            }
//
//        }
//        catch (Exception e)
//        {
//            String message = e.getMessage();
//            return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @RequestMapping(value = "/login",method = RequestMethod.POST)
//    public ResponseEntity login(@RequestBody String json)
//    {
//        try
//        {
//            JsonNode data = objectMapper.readTree(json);
//            User user = service.getUserByStudentNumber(data.findValue("studentNumber").asText());
//            if(user != null)
//            {
//                EncrypPassword encrypPassword = new EncrypPassword();
//                encrypPassword.setPassword(data.findValue("password").asText());
//                String ep = encrypPassword.getPassword();
//                if(user.getPassword().equals(ep))
//                {
//                    return new ResponseEntity(user,HttpStatus.OK);
//                }
//                else
//                {
//                    String message = "Password don't match";
//                    return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
//                }
//            }
//            else
//            {
//                String message = "User not found";
//                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
//            }
//
//        }catch (Exception e){
//            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
//    @RequestMapping(value = "",method = RequestMethod.POST)
//    public ResponseEntity upadetUser(@RequestBody String json)
//    {
//        try {
//            JsonNode data = objectMapper.readTree(json);
//            User user = service.getUserByStudentNumber(data.findValue("studentNumber").asText());
//            if(user!=null)
//            {
//                user.setContact( data.findValue("contact").asText());
//                user.setParentIdNumber(data.findValue("parentIdNumber").asText());
//                user.setParentName(data.findValue("parentName").asText());
//                user.setParentSurname(data.findValue("parentSurname").asText());
//                user.setEmail( data.findValue("email").asText());
//                user.setName(data.findValue("name").asText());
//                user.setSurname( data.findValue("surname").asText());
//                user.setAddress(data.findValue("address").asText());
//                boolean computerLessons = false;
//                boolean swimmingLessons = false;
//                boolean soccerLessons = false;
//                boolean dancingLessons = false;
//                if(data.has("dacingLessons"))
//                {
//                    dancingLessons = data.findValue("dacingLessons").asBoolean();
//
//                }
//                if(data.has("computerLessons"))
//                {
//                    computerLessons = data.findValue("computerLessons").asBoolean();
//                }
//                if(data.has("swimmingLessons"))
//                {
//                    swimmingLessons = data.findValue("swimmingLessons").asBoolean();
//
//                }
//                if(data.has("soccerLessons"))
//                {
//                    soccerLessons = data.findValue("soccerLessons").asBoolean();
//                }
//                user.setComputerLessons(computerLessons);
//                user.setDacingLessons(dancingLessons);
//                user.setSwimmingLessons(swimmingLessons);
//                user.setSoccerLessons(soccerLessons);
//                User user1 = service.updateUser(user);
//                return new ResponseEntity(user1,HttpStatus.OK);
//            }
//            else
//            {
//                String message = "User not found";
//                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
//            }
//
//        }catch (Exception e)
//        {
//            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
//    @RequestMapping(value = "",method = RequestMethod.DELETE)
//    public ResponseEntity deleteUser(@RequestBody String json)
//    {
//        try {
//            JsonNode data = objectMapper.readTree(json);
//            User user = service.getUserByStudentNumber(data.findValue("studentNumber").asText());
//            if(user!=null) {
//                service.deleteUser(user);
//            }
//            return new ResponseEntity(HttpStatus.OK);
//
//
//        }
//        catch (Exception e)
//        {
//            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @RequestMapping(value = "/send/email/user",method = RequestMethod.POST)
//    public ResponseEntity sendEmail(@RequestBody String json)
//    {
//        try {
//            JsonNode data = objectMapper.readTree(json);
//
//            String to = data.findValue("email").asText();
//            String subject = data.findValue("subject").asText();
//            String message = data.findValue("message").asText();
//
//            SendEmail sendEmail = new SendEmail();
//            String mess = sendEmail.sendEmail(to, subject, message);
//
//                return new ResponseEntity(HttpStatus.OK);
//
//
//        }
//        catch (Exception e){
//            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
//    @RequestMapping(value = "/send/email/users",method = RequestMethod.POST)
//    public ResponseEntity sendEmailToAllUsers(@RequestBody String json){
//        try {
//
//            List<User> list = service.getAllUsers();
//            String emails = "";
//            int  count= 0;
//            for (User user:list){
//                count++;
//                if(count == 1)
//                {
//                    emails = user.getEmail();
//                }
//                else{
//                    emails += "," + user.getEmail();
//                }
//
//            }
//            JsonNode data = objectMapper.readTree(json);
//            String subject = data.findValue("subject").asText();
//            String message = data.findValue("message").asText();
//
//            SendEmail sendEmail = new SendEmail();
//            String mess = sendEmail.sendEmail(emails, subject, message);
//                return new ResponseEntity(HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//
//
//}


package za.ac.kidsfountain.Controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.ac.kidsfountain.Entity.User;
import za.ac.kidsfountain.Services.UserService;
import za.ac.kidsfountain.utils.Common;
import za.ac.kidsfountain.utils.EncrypPassword;
import za.ac.kidsfountain.utils.SendEmail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by kidsfountain on 8/12/16.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    ObjectMapper objectMapper = new Common().setUpObjectMapper();


    @Autowired
    UserService service;

    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

    public UserController(){
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity createUser(@RequestBody String json)
    {
        try {
            final JsonNode data = objectMapper.readTree(json);

            User user = new User(data.findValue("name").asText(),
                    data.findValue("surname").asText(),
                    data.findValue("idNumber").asText(),
                    data.findValue("parentName").asText(),
                    data.findValue("parentSurname").asText(),
                    data.findValue("address").asText(),
                    data.findValue("contact").asText(),
                    data.findValue("parentIdNumber").asText(),
                    data.findValue("email").asText());
            boolean computerLessons = false;
            boolean swimmingLessons = false;
            boolean soccerLessons = false;
            boolean dancingLessons = false;
            if(data.has("dacingLessons"))
            {
                dancingLessons = data.findValue("dacingLessons").asBoolean();

            }
            if(data.has("computerLessons"))
            {
                computerLessons = data.findValue("computerLessons").asBoolean();
            }
            if(data.has("swimmingLessons"))
            {
                swimmingLessons = data.findValue("swimmingLessons").asBoolean();

            }
            if(data.has("soccerLessons"))
            {
                soccerLessons = data.findValue("soccerLessons").asBoolean();
            }
            user.setComputerLessons(computerLessons);
            user.setDacingLessons(dancingLessons);
            user.setSwimmingLessons(swimmingLessons);
            user.setSoccerLessons(soccerLessons);

            Random random = new Random();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
            Date today = new Date();
            int year = today.getYear();
            System.out.println(year + " --------------");


            String studentNumber = "2017" + random.nextInt(10000);
            boolean valid = false;
            if(service.getUserByStudentNumber(studentNumber) == null && studentNumber.length() == 8)
            {
                valid =true;
            }
            while (!valid)
            {
                studentNumber = "2017" + random.nextInt(10000);
                if(service.getUserByStudentNumber(studentNumber) == null && studentNumber.length() == 8)
                {
                    valid =true;
                }
            }
            if(service.getStudentByIdNumber(user.getIdNumber()) == null)
            {
                user.setStudentNumber(studentNumber);
                user.setApplicationStatus("Application received, waiting for admin approval");
                User user1 = service.createUser(user);
                String email = user1.getEmail();
                String subject = "Application";
                String body = "Hi " + user1.getName() + "\n\nYour application has successfully received, just wait for admin approval \n\n\nYour student number is: "+ user1.getStudentNumber() +
                        " use it to sign in\n\n\n\n\nWarmest regards\nKids fountain";
                SendEmail sendEmail = new SendEmail();
                String mess = sendEmail.sendEmail(email, subject, body);
                return new ResponseEntity(user1,HttpStatus.CREATED);

            }
            else
            {
                //String message = "Student id number already exist...";
                user.setApplicationStatus("Student id number already exist...");

                return new ResponseEntity(user,HttpStatus.OK);
            }


        }
        catch (Exception e){
            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        }

    }
    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseEntity getAllUsers()
    {
        try
        {
            return new ResponseEntity<>(service.getAllUsers(),HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public ResponseEntity approve(@RequestBody String user)
    {
        try
        {
            JsonNode data = objectMapper.readTree(user);
            User u = service.getUserByStudentNumber(data.findValue("studentNumber").asText());
            if(u != null)
            {
                User user1 = service.updateStatus(u.getStudentNumber(),"Approved");

                String email = user1.getEmail();
                String subject = "Application Status";
                String body = "Hi " + user1.getName() + "\nstudent number: " + user1.getStudentNumber() + "\n\nYour application has Approved, you be alerted on registration procedure" +
                        "\n\nWarmest regards\nKidz fountains";

                SendEmail sendEmail = new SendEmail();
                sendEmail.sendEmail(email, subject, body);
                return new ResponseEntity(user1,HttpStatus.OK);
            }
            else
            {
                String message = "user not found";

                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
            }


        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/deregister", method = RequestMethod.POST)
    public ResponseEntity deRegister(@RequestBody String user)
    {
        try
        {
            JsonNode data = objectMapper.readTree(user);
            User u = service.getUserByStudentNumber(data.findValue("studentNumber").asText());
            if(u != null)
            {
                User user1 = service.updateStatus(u.getStudentNumber(),"You are de-activated from the system, Consult the admin.....");
                String email = user1.getEmail();
                String subject = "Registration de-activation";
                String body = "Hi " + user1.getName() + "\nStudent Number: "+ user1.getStudentNumber()+"\n\nYou have been de-activated from the system...for more details please consult the admins"
                        + "\n\nWarmest regards\nKidz fountains";

                SendEmail sendEmail = new SendEmail();
                sendEmail.sendEmail(email, subject, body);

                return new ResponseEntity(user1,HttpStatus.OK);
            }
            else
            {
                String message = "user not found";

                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
            }


        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/decline", method = RequestMethod.POST)
    public ResponseEntity decline(@RequestBody String user)
    {
        try
        {
            JsonNode data = objectMapper.readTree(user);
            User u = service.getUserByStudentNumber(data.findValue("studentNumber").asText());
            if(u != null)
            {
                User user1 = service.updateStatus(u.getStudentNumber(),"Declined");
                String email = user1.getEmail();
                String subject = "Application status";
                String body = "Hi " + user1.getName() + "\nStudent Number: "+ user1.getStudentNumber()+"\n\nYour application has declined, please consult the admins for more info"
                        + "\n\nWarmest regards\nKidz fountains";
                SendEmail sendEmail = new SendEmail();
                sendEmail.sendEmail(email, subject, body);
                return new ResponseEntity(user1,HttpStatus.OK);
            }
            else
            {
                String message = "user not found";
                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
            }


        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody String user)
    {
        try
        {
            JsonNode data = objectMapper.readTree(user);
            User u = service.getUserByStudentNumber(data.findValue("studentNumber").asText());

            if(u != null)
            {
                if(u.getApplicationStatus().equalsIgnoreCase("Registered"))
                {
                    String message = "Already registered";
                    return new ResponseEntity(message,HttpStatus.OK);
                }
                else
                {
                    User user1 = service.updateStatus(u.getStudentNumber(),"Registered");
                    return new ResponseEntity(user1,HttpStatus.OK);
                }


            }
            else
            {
                String message = "user not found";
                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
            }


        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity getSingleUser(@RequestBody String user)
    {
        try
        {
            JsonNode data = objectMapper.readTree(user);
            User u = service.getUserByStudentNumber(data.findValue("studentNumber").asText());
            System.out.println("++++++++++++" + u.isComputerLessons());
            if(u != null)
            {
                return new ResponseEntity(u,HttpStatus.OK);
            }
            else
            {
                String message = "user not found";
                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
            }


        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/user/idNumber", method = RequestMethod.POST)
    public ResponseEntity getSingleUserById(@RequestBody String user)
    {
        try
        {
            JsonNode data = objectMapper.readTree(user);
            User u = service.getStudentByIdNumber(data.findValue("idNumber").asText());
            if(u != null)
            {
                return new ResponseEntity(u,HttpStatus.OK);
            }
            else
            {
                String message = "user not found";
                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
            }


        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/setpassword",method = RequestMethod.POST)
    public ResponseEntity setPassword(@RequestBody String json)
    {
        try {
            JsonNode data = objectMapper.readTree(json);
            User user = service.getUserByStudentNumber(data.findValue("studentNumber").asText());
            if(user != null)
            {
                EncrypPassword encrypPassword = new EncrypPassword();
                encrypPassword.setPassword(data.findValue("password").asText());
                String ep = encrypPassword.getPassword();


                User user1 = service.setPassword(user.getStudentNumber(),ep);
                String email = user1.getEmail();
                String subject = "Password confirmation";
                String body = "Hi " + user1.getName() + "\nStudent Number: "+ user1.getStudentNumber()+"\n\nYour have successfully set your password nad your password is" + user.getPassword()
                        + "\n\nWarmest regards\nKids fountain";
                SendEmail sendEmail = new SendEmail();
                sendEmail.sendEmail(email, subject, body);
                return new ResponseEntity(user1,HttpStatus.OK);
            }
            else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

        }
        catch (Exception e)
        {
            String message = e.getMessage();
            return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/clearpassword",method = RequestMethod.POST)
    public ResponseEntity clearPassword(@RequestBody String json)
    {
        try {
            JsonNode data = objectMapper.readTree(json);
            User user = service.getUserByStudentNumber(data.findValue("studentNumber").asText());
            if(user != null)
            {
                return new ResponseEntity(service.clearPassword(user.getStudentNumber()),HttpStatus.OK);
            }
            else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

        }
        catch (Exception e)
        {
            String message = e.getMessage();
            return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody String json)
    {
        try
        {
            JsonNode data = objectMapper.readTree(json);
            User user = service.getUserByStudentNumber(data.findValue("studentNumber").asText());
            if(user != null)
            {
                EncrypPassword encrypPassword = new EncrypPassword();
                encrypPassword.setPassword(data.findValue("password").asText());
                String ep = encrypPassword.getPassword();
                if(user.getPassword().equals(ep))
                {
                    return new ResponseEntity(user,HttpStatus.OK);
                }
                else
                {
                    String message = "Password don't match";
                    return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
                }
            }
            else
            {
                String message = "User not found";
                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
            }

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseEntity upadetUser(@RequestBody String json)
    {
        try {
            JsonNode data = objectMapper.readTree(json);
            User user = service.getUserByStudentNumber(data.findValue("studentNumber").asText());
            if(user!=null)
            {
                user.setContact( data.findValue("contact").asText());
                user.setParentIdNumber(data.findValue("parentIdNumber").asText());
                user.setParentName(data.findValue("parentName").asText());
                user.setParentSurname(data.findValue("parentSurname").asText());
                user.setEmail( data.findValue("email").asText());
                user.setName(data.findValue("name").asText());
                user.setSurname( data.findValue("surname").asText());
                user.setAddress(data.findValue("address").asText());
                boolean computerLessons = false;
                boolean swimmingLessons = false;
                boolean soccerLessons = false;
                boolean dancingLessons = false;
                if(data.has("dacingLessons"))
                {
                    dancingLessons = data.findValue("dacingLessons").asBoolean();

                }
                if(data.has("computerLessons"))
                {
                    computerLessons = data.findValue("computerLessons").asBoolean();
                }
                if(data.has("swimmingLessons"))
                {
                    swimmingLessons = data.findValue("swimmingLessons").asBoolean();

                }
                if(data.has("soccerLessons"))
                {
                    soccerLessons = data.findValue("soccerLessons").asBoolean();
                }
                user.setComputerLessons(computerLessons);
                user.setDacingLessons(dancingLessons);
                user.setSwimmingLessons(swimmingLessons);
                user.setSoccerLessons(soccerLessons);
                User user1 = service.updateUser(user);
                return new ResponseEntity(user1,HttpStatus.OK);
            }
            else
            {
                String message = "User not found";
                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
            }

        }catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "",method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@RequestBody String json)
    {
        try {
            JsonNode data = objectMapper.readTree(json);
            User user = service.getUserByStudentNumber(data.findValue("studentNumber").asText());
            if(user!=null) {
                service.deleteUser(user);
            }
            return new ResponseEntity(HttpStatus.OK);


        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/send/email/user",method = RequestMethod.POST)
    public ResponseEntity sendEmail(@RequestBody String json)
    {
        try {
            JsonNode data = objectMapper.readTree(json);

            String to = data.findValue("email").asText();
            String subject = data.findValue("subject").asText();
            String message = data.findValue("message").asText();

            SendEmail sendEmail = new SendEmail();
            String mess = sendEmail.sendEmail(to, subject, message);

            return new ResponseEntity(HttpStatus.OK);


        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/send/email/users",method = RequestMethod.POST)
    public ResponseEntity sendEmailToAllUsers(@RequestBody String json){
        try {

            List<User> list = service.getAllUsers();
            String emails = "";
            int  count= 0;
            for (User user:list){
                count++;
                if(count == 1)
                {
                    emails = user.getEmail();
                }
                else{
                    emails += "," + user.getEmail();
                }

            }
            JsonNode data = objectMapper.readTree(json);
            String subject = data.findValue("subject").asText();
            String message = data.findValue("message").asText();

            SendEmail sendEmail = new SendEmail();
            String mess = sendEmail.sendEmail(emails, subject, message);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }




}

