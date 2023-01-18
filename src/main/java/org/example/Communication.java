package org.example;

import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class Communication {

    private final RestTemplate restTemplate;
    private final String URL = "http://94.198.50.185:7081/api/users";
    private String cookie;
    private final HttpHeaders httpHeaders = new HttpHeaders();

    @Autowired
    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUsers(){

        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});

        cookie = responseEntity.getHeaders().get("Set-Cookie").get(0).split(";")[0].split("=")[1];
        System.out.println(cookie);

        return responseEntity.getBody();
    }

    public void saveUser(User user) {

        httpHeaders.add("Session-Id", cookie);
        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, httpEntity, String.class);
        System.out.println(responseEntity.getBody());

    }

//    public void updateUser(User newUser) {
//
//        httpHeaders.add("Session-Id", cookie);
//        HttpEntity<User> httpEntity = new HttpEntity<>(newUser, httpHeaders);
//        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, httpEntity, String.class);
//        System.out.println(responseEntity.getBody());
//
//    }
//
//    public void deleteUser(Long id) {
//gi
//        restTemplate.delete(URL + "/" + id);
//    }
}
