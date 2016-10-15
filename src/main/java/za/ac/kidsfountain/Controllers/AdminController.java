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
import za.ac.kidsfountain.Entity.Admin;
import za.ac.kidsfountain.Services.AdminService;
import za.ac.kidsfountain.utils.EncrypPassword;
import za.ac.kidsfountain.utils.SendEmail;

import java.util.List;
import java.util.Random;

/**
 * Created by kidsfountain on 8/20/16.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    AdminService service;
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

    public AdminController(){
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity createAdmin(@RequestBody String json) {
        try
        {
            final JsonNode data = mapper.readTree(json);
            Admin admin = new Admin(data.findValue("name").asText(),
                    data.findValue("surname").asText(),
                    data.findValue("idNumber").asText(),
                    data.findValue("contact").asText(),
                    data.findValue("address").asText(),
                    data.findValue("email").asText());


            Random ran = new Random();
            String staffNumber = data.findValue("idNumber").asText().substring(0,6) + ran.nextInt(10000);
            List<Admin> admins = service.getAllAdmin();
            boolean valid = false;
            if(service.getAdminByStaffNumber(staffNumber) == null && staffNumber.length() == 10)
            {
                valid =true;
            }
            while(!valid)
            {
                staffNumber = data.findValue("idNumber").asText().substring(0,6) + ran.nextInt(10000);
                if(service.getAdminByStaffNumber(staffNumber) == null && staffNumber.length() == 10)
                {
                    valid =true;
                }
            }

            admin.setStaffNumber(staffNumber);
            Admin checkAdminByEmail = service.getAdminByEmail(data.findValue("email").asText());
            Admin checkAdminById= service.getAdminByIdNumber(data.findValue("idNumber").asText());
            System.out.println(checkAdminByEmail);
            if(checkAdminByEmail ==  null)
            {
                if(checkAdminById ==  null)
                {
                    SendEmail sendEmail = new SendEmail();
                    sendEmail.sendEmail(admin.getEmail(),"Registration","Hi " + admin.getName() + "\n\nYou are successfully registered to the kidz fountain system as admin\n\n\nYour staff number is:"+ admin.getStaffNumber() +
                                                " use it to sign in\n\n\n\n\nWarmest regards\nKids fountain");
                    Admin a = service.createUser(admin);
                    return new ResponseEntity(a, HttpStatus.CREATED);
                }
                else
                {
                    String message = "ID Number already in use";
                    checkAdminById.setIdNumber(message);
                    return new ResponseEntity(checkAdminById,HttpStatus.OK);
                }

            }
            else
            {

                String message = "Email already in use";
                checkAdminById.setIdNumber(message);
                return new ResponseEntity(checkAdminById,HttpStatus.OK);
            }



        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/setpassword", method = RequestMethod.POST)
    public ResponseEntity updatePassword(@RequestBody String json) {
        try {
            final JsonNode data = mapper.readTree(json);

            Admin admin = service.getAdminByStaffNumber(data.findValue("staffNumber").asText());
            if(admin != null)
            {
                EncrypPassword encrypPassword = new EncrypPassword();
                encrypPassword.setPassword(data.findValue("password").asText());
                String ep = encrypPassword.getPassword();
                return new ResponseEntity(service.setPassword(data.findValue("staffNumber").asText(),ep),HttpStatus.OK);
            }
            else
            {
                String message = "Staff Number not found";
                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            String message = "Staff Number not found";
            return new ResponseEntity(message,HttpStatus.NOT_FOUND);
        }

    }
    @RequestMapping(value = "/clearpassword", method = RequestMethod.POST)
    public ResponseEntity clearPassword(@RequestBody String json)
    {
        try {
            final JsonNode data = mapper.readTree(json);

            Admin admin = service.getAdminByStaffNumber(data.findValue("staffNumber").asText());
            if(admin != null)
            {
                return new ResponseEntity(service.clearPassword(data.findValue("staffNumber").asText()),HttpStatus.OK);
            }
            else
            {
                String message = "Staff Number not found";
                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            String message = "Staff Number not found";
            return new ResponseEntity(message,HttpStatus.NOT_FOUND);
        }

    }
    @RequestMapping(value = "/staff", method = RequestMethod.POST)
    public  ResponseEntity getBystaffNumber(@RequestBody String json){

        try {
            final JsonNode data = mapper.readTree(json);
            String staffNumber = data.findValue("staffNumber").asText();
            Admin admin = service.getAdminByStaffNumber(staffNumber);
            if(admin != null)
            {
                return new ResponseEntity(admin,HttpStatus.OK);
            }
            else
            {
                String message = "Staff Number not found";
                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
            }

        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        }


    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody String json)
    {
        try {

            final JsonNode data = mapper.readTree(json);
            System.out.print(data);
              Admin admin = service.getAdminByStaffNumber(data.findValue("staffNumber").asText());
            EncrypPassword encrypPassword = new EncrypPassword();
            encrypPassword.setPassword(data.findValue("password").asText());
            String ep = encrypPassword.getPassword();
                if(admin.getPassword().equals(ep))
                {
                    return new ResponseEntity(admin,HttpStatus.OK);
                }
                else
                {
                    String message = "You have  enterd wrong password";
                    return  new ResponseEntity(message,HttpStatus.NOT_FOUND);
                }


        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity getAllAdmin()
    {
        try {
            return new ResponseEntity<>(service.getAllAdmin(),HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/staff/staffNumber", method = RequestMethod.POST)
    public ResponseEntity getAdminBySN(@RequestBody String staffNumber)
    {
        try {
            final JsonNode data = mapper.readTree(staffNumber);
            System.out.println(data.findValue("staffNumber").asText());

            Admin admin = service.getAdminByStaffNumber(data.findValue("staffNumber").asText());
            if(admin != null)
            {
                return new ResponseEntity(admin,HttpStatus.OK);
            }
            else {
                String message = "Staff Number not found";
                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
            }

        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        }

    }
    @RequestMapping(name = "",method = RequestMethod.DELETE)
    public ResponseEntity deleteAdmin(@RequestBody String json)
    {
        try {
            JsonNode data = mapper.readTree(json);
            Admin admin = service.getAdminByStaffNumber(data.findValue("staffNumber").asText());

                service.deleteAdmin(admin);
                return new ResponseEntity(HttpStatus.OK);


        }
        catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(name = "",method = RequestMethod.POST)
    public ResponseEntity updateAdmin(@RequestBody String json)
    {
        try{
            JsonNode data = mapper.readTree(json);
            Admin admin = service.getAdminByStaffNumber(data.findValue("staffNumber").asText());


            if(admin != null)
            {
                SendEmail send = new SendEmail();

                admin.setEmail(data.findValue("email").asText());
                admin.setAddress(data.findValue("address").asText());
                admin.setContact(data.findValue("contact").asText());
                admin.setName(data.findValue("name").asText());
                admin.setSurname(data.findValue("surname").asText());
                Admin a = service.updateAdmin(admin);
                send.sendEmail(data.findValue("email").asText(),"Registration confirmation","lol");
                return  new ResponseEntity(a,HttpStatus.OK);
            }else{
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/staff/send/email",method = RequestMethod.POST)
    public ResponseEntity sendEmail(@RequestBody String json){
        try {
            JsonNode data = mapper.readTree(json);

            String to = data.findValue("email").asText();
            String subject = data.findValue("subject").asText();
            String message = data.findValue("message").asText();

            SendEmail sendEmail = new SendEmail();
            sendEmail.sendEmail(to,subject,message);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value ="/staff/send/email/all",method = RequestMethod.POST)
    public ResponseEntity sendEmailToAllStaff(@RequestBody String json){
        try {
            JsonNode data = mapper.readTree(json);
            String subject = data.findValue("subject").asText();
            String message = data.findValue("message").asText();

            List<Admin> list = service.getAllAdmin();
            String emails = "";
            int  count= 0;
            for (Admin user:list){
                count++;
                if(count == 1)
                {
                    emails = user.getEmail();
                }
                else{
                    emails += "," + user.getEmail();
                }

            }
            SendEmail sendEmail = new SendEmail();
            sendEmail.sendEmail(emails,subject,message);
            return new ResponseEntity(HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }




}
