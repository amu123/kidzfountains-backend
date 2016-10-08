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
import za.ac.kidsfountain.Entity.Application;
import za.ac.kidsfountain.Services.ApplicationService;

/**
 * Created by kidsfountain on 9/15/16.
 */
@RestController
@RequestMapping("/application")
public class ApplicationController {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    ApplicationService service;
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    public ApplicationController(){
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");

    }
    @RequestMapping(value = "/setApplicationStatus",method = RequestMethod.POST)
    public ResponseEntity setApplicationStatus(@RequestBody String json){
        try {
            JsonNode data = mapper.readTree(json);
            String status = data.findValue("status").asText();
            Application application = service.saveApplicationStatus(status);
            return new ResponseEntity(application,HttpStatus.OK);

        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/setRegistrationStatus",method = RequestMethod.POST)
    public ResponseEntity setRegistrationStatus(@RequestBody String json){
        try {
            JsonNode data = mapper.readTree(json);
            String status = data.findValue("status").asText();
            Application application = service.setRegistration(status);
            return new ResponseEntity(application,HttpStatus.OK);

        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/getApplication",method = RequestMethod.POST)
    public ResponseEntity getAppl()
    {
        try {

            Application application = service.getApplication(1L);
            System.out.println("--------- Application " + application);
            return new ResponseEntity(application,HttpStatus.OK);

        }
        catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
