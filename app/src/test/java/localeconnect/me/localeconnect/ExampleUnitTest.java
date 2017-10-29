package localeconnect.me.localeconnect;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE)
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);


        String url = "http://rest-service.guides.spring.io/greeting";
        url = "http://localhost:9000/eventserv/";

        Event user = new Event();
        user.setAcceptingUserId(
                "sdfdsdfsfd");
        user.setEventTime(new Date());
        user.setAddress(String.valueOf(System.currentTimeMillis()));

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpEntity<Event> request = new HttpEntity<>(user);
        ResponseEntity<Event> entity = restTemplate.exchange(url, HttpMethod.POST,
                request, Event.class);
    }
}