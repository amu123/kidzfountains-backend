package za.ac.kidsfountain.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Created by kidsfountain on 8/12/16.
 */
public class Common {
    public ObjectMapper setUpObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        //mapper.registerModule(new JodaModule());
        SimpleModule simpleModule = new SimpleModule();
        //simpleModule.addSerializer(Money.class,new MoneySerializer());
        //simpleModule.addDeserializer(Money.class,new MoneyDeserializer());
        mapper.registerModule(simpleModule);
        return mapper;
    }
}
