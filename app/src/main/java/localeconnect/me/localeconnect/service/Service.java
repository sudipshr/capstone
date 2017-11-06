package localeconnect.me.localeconnect.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import localeconnect.me.localeconnect.Event;
import localeconnect.me.localeconnect.User;

/**
 * Created by admin on 10/29/2017.
 */

public class Service {

    public final static String BASEURL = "http://localhost:9000";
    public static final String USERSERV_URI = BASEURL+"/userserv";
    public static final String REG_URI = USERSERV_URI+"/register";
    public static final String LOGIN_URI = USERSERV_URI+"/auth";
    public static final String EVENTSERV_URI = BASEURL+"/eventserv";

    public static void main(String[] args){
        System.out.println(REG_URI);
    }
    public User register(User user){


        HttpEntity<User> request = new HttpEntity<User>(user);
        ResponseEntity<User> entity = ServiceUtil.getRestTemplate().exchange(REG_URI, HttpMethod.POST,
                request, User.class);

        return entity.getBody();
    }

    public User login(User user){

        HttpEntity<User> request = new HttpEntity<User>(user);
        ResponseEntity<User> entity = ServiceUtil.getRestTemplate().exchange(LOGIN_URI, HttpMethod.POST,
                request, User.class);

        return entity.getBody();
    }

    public User logout(User user){

        return null;
    }

    public Event createEvent(Event event){

        HttpEntity<Event> request = new HttpEntity<>(event);
        ResponseEntity<Event> entity = ServiceUtil.getRestTemplate().exchange(LOGIN_URI, HttpMethod.POST,
                request, Event.class);

        return entity.getBody();

    }

    public List<Event> getEvents(){

        ResponseEntity<Object[]> responseEntity = ServiceUtil.getRestTemplate().getForEntity(LOGIN_URI, Object[].class);
        Object[] objects = responseEntity.getBody();

        return Arrays.asList((Event[])(objects));

    }

    public Event getEvent(Event event){

        ResponseEntity<Event> entity = ServiceUtil.getRestTemplate().getForEntity(EVENTSERV_URI+"/"+event.getId(), Event.class);
        return entity.getBody();

    }
}
