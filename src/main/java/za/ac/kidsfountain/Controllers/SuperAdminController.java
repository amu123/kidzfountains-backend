package za.ac.kidsfountain.Controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.ac.kidsfountain.Entity.SuperAdmin;
import za.ac.kidsfountain.Services.SuperAdminService;

/**
 * Created by kidsfountain on 9/8/16.
 */
@RestController
@RequestMapping("/superadmin")
public class SuperAdminController {
    @Autowired
    SuperAdminService service;

    ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "",method = RequestMethod.PUT)
    public ResponseEntity createAdmin(@RequestBody String json)
    {
        try {
            JsonNode data = mapper.readTree(json);
            SuperAdmin admin = new SuperAdmin(data.findValue("password").asText(),data.findValue("adminNumber").asLong());
            service.createAdim(admin);
            return new ResponseEntity(HttpStatus.CREATED);

        }
        catch (Exception e)
        {
            String message = "Something is wrong";
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/admin",method = RequestMethod.POST)
    public ResponseEntity getAdmin(@RequestBody String json)
    {
        try {
            JsonNode data = mapper.readTree(json);
            long adminNumber = data.findValue("adminNumber").asLong();
            SuperAdmin admin = service.getSuperAdmin(adminNumber);
            if(admin != null)
            {
                return new ResponseEntity(admin,HttpStatus.OK);
            }
            else
            {
                String message= "Admin not found";
                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            String message= "Please be careful";
            return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody String json)
    {
        try {
            JsonNode data = mapper.readTree(json);
            long adminNumber = data.findValue("adminNumber").asLong();
            String password = data.findValue("password").asText();
            SuperAdmin admin = service.getSuperAdmin(adminNumber);
            if(admin != null)
            {
                if(admin.getPassword().equals(password))
                {
                    return new ResponseEntity(admin,HttpStatus.OK);
                }
                else {
                    String message= "You have entered wrong password";
                    return new ResponseEntity(message,HttpStatus.NOT_FOUND);
                }

            }
            else
            {
                String message= "You have entered wrong super admin number";
                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            String message= "Please be careful";
            return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
        }
    }

}
