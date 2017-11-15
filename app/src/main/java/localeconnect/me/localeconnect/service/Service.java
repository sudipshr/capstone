package localeconnect.me.localeconnect.service;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import localeconnect.me.localeconnect.BuildConfig;
import localeconnect.me.localeconnect.Event;
import localeconnect.me.localeconnect.Preference;
import localeconnect.me.localeconnect.User;

/**
 * Created by admin on 10/29/2017.
 */

public class Service {

    public final static String BASEURL = "http://54.213.250.86";
    public static final String USERSERV_URI = BASEURL + "/userserv";
    public static final String REG_URI = USERSERV_URI + "/register";
    public static final String LOGIN_URI = USERSERV_URI + "/auth";
    public static final String EVENTSERV_URI = BASEURL + "/eventserv";

    public static void main(String[] args) {
        System.out.println(REG_URI);
        System.out.println(EVENTSERV_URI);
    }


    public Service() {

        // Get Remote Config instance.
        // [START get_remote_config_instance]
        //FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        // [END get_remote_config_instance]

        // Create a Remote Config Setting to enable developer mode, which you can use to increase
        // the number of fetches available per hour during development. See Best Practices in the
        // README for more information.
        // [START enable_dev_mode]
        //FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
        //        .setDeveloperModeEnabled(BuildConfig.DEBUG)
         //       .build();
        //mFirebaseRemoteConfig.setConfigSettings(configSettings);

    }

    public User register(User user) {


        HttpEntity<User> request = new HttpEntity<User>(user);
        ResponseEntity<User> entity = ServiceUtil.getRestTemplate().exchange(REG_URI, HttpMethod.POST,
                request, User.class);

        return entity.getBody();
    }

    public User login(User user) {

        HttpEntity<User> request = new HttpEntity<User>(user);
        ResponseEntity<User> entity = ServiceUtil.getRestTemplate().exchange(LOGIN_URI, HttpMethod.POST,
                request, User.class);

        return entity.getBody();
    }

    public User logout(User user) {

        return null;
    }

    public Event createEvent(Event event) {



        event.setAcceptingUserId(String.valueOf(System.currentTimeMillis()));
        HttpEntity<Event> request = new HttpEntity<Event>(event);
        ResponseEntity<Event> entity = ServiceUtil.getRestTemplate().exchange(EVENTSERV_URI, HttpMethod.POST,
                request, Event.class);


        return entity.getBody();

    }

    public List<Event> getEvents(String userId) {

        ResponseEntity<Event[]> responseEntity = ServiceUtil.getRestTemplate().getForEntity(EVENTSERV_URI+ "/matchingEvents?userId="+userId, Event[].class);
        Event[] objects = responseEntity.getBody();

        return Arrays.asList(objects);

    }

    public Event getEventsss(Event event) {

        ResponseEntity<Event> entity = ServiceUtil.getRestTemplate().getForEntity(EVENTSERV_URI + "/events?userId=test", Event.class);
        return entity.getBody();

    }

    public List<Preference> getPreferences(String userId) {

        ResponseEntity<Preference[]> responseEntity = ServiceUtil.getRestTemplate().getForEntity(USERSERV_URI+ "/getPreferences?userId="+userId, Preference[].class);
        Preference[] objects = responseEntity.getBody();

        return Arrays.asList(objects);

    }

    public List<Preference> createPreference(Preference preference) {

        HttpEntity<Preference> request = new HttpEntity<Preference>(preference);
        ResponseEntity<Preference> responseEntity = ServiceUtil.getRestTemplate().exchange(USERSERV_URI+"/createPreference",
                HttpMethod.POST,
                request, Preference.class);
        Preference objects = responseEntity.getBody();

        return getPreferences(objects.getUserId());

    }
}
