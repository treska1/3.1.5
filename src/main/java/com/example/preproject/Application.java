package com.example.preproject;

import com.example.preproject.model.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application {

    private final static String URL = "http://94.198.50.185:7081/api/users";
    private static RestTemplate restTemplate = new RestTemplate();
    private static ResponseEntity<String> responseEntity;

    public static void main(String[] args) {
        useRestTemplate();
    }

    public static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        responseEntity = restTemplate.getForEntity(URL, String.class );
        List<String> cookies = responseEntity.getHeaders().get("Set-Cookie");
        headers.set("Cookie", cookies.stream().collect(Collectors.joining(";")));
        System.out.println(headers);
        return headers;
    }

    public static void useRestTemplate(){
        HttpHeaders headers =getHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        User user = new User(3L, "James", "Brown", (byte) 10);
        User newUser = new User(3L, "Thomas", "Shelby", (byte) 10);
        addUser(user, headers);
        updateUser(newUser, headers);
        deleteUser(3,headers);
    }

    private static void deleteUser(int id, HttpHeaders httpHeaders) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL+"/" +id, HttpMethod.DELETE, new HttpEntity<>(id, httpHeaders), String.class);
        System.out.println(responseEntity.getBody());
    }

    private static void updateUser(User user, HttpHeaders httpHeaders) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(user, httpHeaders), String.class);
        System.out.println(responseEntity.getBody());
    }

    private static void addUser(User user, HttpHeaders httpHeaders) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, new HttpEntity<>(user, httpHeaders), String.class);
        System.out.println(responseEntity.getBody());
    }
}


