package za.ac.kidsfountain.Controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import za.ac.kidsfountain.Entity.Events;
import za.ac.kidsfountain.Services.EventsService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tlharihani on 9/22/16.
 */
@RestController
@RequestMapping("/events")
public class EventsController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    ObjectMapper mapper = new ObjectMapper();

    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    @Autowired
    EventsService service;

    public EventsController(){
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
    }
    @RequestMapping(value = "/create",method = RequestMethod.PUT)
    public ResponseEntity createEvent(@RequestBody String json)
    {
        try {
            JsonNode data = mapper.readTree(json);

            Events event = new Events(data.findValue("title").asText(),
                                    data.findValue("description").asText(),
                                    data.findValue("date").asText(),
                                    data.findValue("postedDate").asText(),
                                    data.findValue("isActive").asBoolean());
            Events e = service.create(event);
            return new ResponseEntity(e,HttpStatus.CREATED);

        }catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResponseEntity upatedEvent(@RequestBody String json)
    {
        try {
            JsonNode data = mapper.readTree(json);
            Events ev = service.getEvent(data.findValue("id").asLong());
            if(ev != null)
            {
                ev.setDate(data.findValue("date").asText());
                ev.setDescription(data.findValue("description").asText());
                ev.setIsActive(data.findValue("isActive").asBoolean());
                ev.setTitle(data.findValue("title").asText());
                ev.setPostedDate(data.findValue("postedDate").asText());

                return new ResponseEntity(service.updateEvent(ev),HttpStatus.OK);
            }
            else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }



        }
        catch (Exception e)
        {
            String message = ("An exception occurred while updating a event: " + e.getLocalizedMessage());
            logger.error(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public ResponseEntity delete(@RequestBody String json){
        try {
            JsonNode data = mapper.readTree(json);
            Events ev = service.getEvent(data.findValue("id").asLong());
            if(ev != null) {
                service.deleteEvent(data.findValue("id").asLong());
                return new ResponseEntity(HttpStatus.OK);
            }
            else {
                String message = "Event does not exist";
                logger.error(message);
                return new ResponseEntity(message, headers, HttpStatus.BAD_REQUEST);
            }



        }catch (Exception e){
            String message = ("An exception occurred while updating a event: " + e.getLocalizedMessage());
            logger.error(message);
            return new ResponseEntity(message, headers, HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseEntity getAllEvents(){
        try {

            List<Events> events = service.getllEvent();
            if(events.isEmpty()){
                String message = "No events on the database available currently";
                logger.error(message);
                return new ResponseEntity(message,HttpStatus.NOT_FOUND);
            }
            else
            {
                return new ResponseEntity(events,HttpStatus.OK);
            }
        }
        catch (Exception e){
            String message = ("An exception occurred while updating a event: " + e.getLocalizedMessage());
            logger.error(message);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/active",method = RequestMethod.GET)
    public ResponseEntity getActive(){
        try {
            List<Events> list = service.getllEvent();
            List<Events> listToBeReturned = new ArrayList<>();
                if(!list.isEmpty()) {
                    for (Events event : list) {

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
                        Date today = new Date();
                        Date date = dateFormat.parse(event.getDate());
                        if(date.before(dateFormat.parse(dateFormat.format(today)))){
                            event.setIsActive(false);
                            event = service.updateEvent(event);

                        }
                        if (date.after(dateFormat.parse(dateFormat.format(today)))) {
                            if (event.isActive()) {
                                listToBeReturned.add(event);

                            }
                        }
                        if(date.equals(dateFormat.parse(dateFormat.format(today)))){
                            event.setIsActive(true);
                            event = service.updateEvent(event);
                            listToBeReturned.add(event);
                            System.out.println( " -----+++++++++ " + event.getDate() + "======" + today);
                        }



                    }
                }
                return new ResponseEntity(listToBeReturned,HttpStatus.OK);


        }catch (Exception e){
            String message = ("An exception occurred while updating a event: " + e.getLocalizedMessage());
            logger.error(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/notActive",method = RequestMethod.GET)
    public ResponseEntity getNotActive(){
        try {
            List<Events> list = service.getllEvent();
            List<Events> listToBeReturned = new ArrayList<>();

            if(!list.isEmpty()) {
                for (Events event : list) {

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
                    Date today = new Date();
                    Date date = dateFormat.parse(event.getDate());
                    if(date.equals(dateFormat.parse(dateFormat.format(today))))
                    {
                        event.setIsActive(true);
                        event =  service.updateEvent(event);
                        System.out.println( " -----" + event.getDate());
                    }
                    else
                    if (date.before(dateFormat.parse(dateFormat.format(today)))) {
                        event.setIsActive(false);
                        event =  service.updateEvent(event);
                        System.out.println( " -----===== before " + event.getDate() + " +++++++++++" + today);


                    }


                    if (!event.isActive()) {
                        listToBeReturned.add(event);
                    }

                }
            }
                return new ResponseEntity(listToBeReturned,HttpStatus.OK);


        }catch (Exception e){
            String message = ("An exception occurred while updating a event: " + e.getLocalizedMessage());
            logger.error(message);
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
    }



}
