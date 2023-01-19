package org.example;

import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class Communication {

    private final RestTemplate restTemplate;
    private final String URL = "http://94.198.50.185:7081/api/users";
    private String cookie;

    @Autowired
    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUsers(){
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});

        cookie = responseEntity.getHeaders().get("Set-Cookie").get(0).split(";")[0].split("=")[1];
        return responseEntity.getBody();
    }

    public String saveUser(User user) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JSESSIONID=" + cookie);
        HttpEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, new HttpEntity<>(user, httpHeaders), String.class);
        return responseEntity.getBody();
    }

    public String updateUser(Long id, User user) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JSESSIONID=" + cookie);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(user, httpHeaders), String.class);
        return responseEntity.getBody();
    }

    public String deleteUser(Long id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JSESSIONID=" + cookie);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, new HttpEntity<>(httpHeaders), String.class);
        return responseEntity.getBody();
    }
}
