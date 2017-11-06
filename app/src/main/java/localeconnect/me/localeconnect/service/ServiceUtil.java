package localeconnect.me.localeconnect.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import localeconnect.me.localeconnect.Event;

/**
 * Created by admin on 10/29/2017.
 */

public class ServiceUtil {



    public static RestTemplate getRestTemplate(){

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        return restTemplate;
    }






}
