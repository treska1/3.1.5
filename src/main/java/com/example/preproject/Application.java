package com.example.preproject;

import com.example.preproject.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

    private final static String URL = "http://94.198.50.185:7081/api/users";

    private static   RestTemplate restTemplate = new RestTemplate();



    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);

        getRestTemplate();
    }

    public static void getRestTemplate(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User> entity = new HttpEntity<>(headers);
        getAllUsers(entity);

        User newUser = new User(3L,"James", "Brown", (byte) 10);
            entity = new HttpEntity<>(newUser,headers);
        create(entity);

    }

    private static void update(HttpEntity<User> entity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
        responseEntity.getHeaders().get("Set-Cookie").stream().forEach(System.out::println);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        System.out.println(httpStatus);
        String users = responseEntity.getBody();
        System.out.println(users);
        responseEntity.getHeaders().get("Set-Cookie");
        HttpHeaders httpHeaders = responseEntity.getHeaders();
        System.out.println(httpHeaders);
    }

    private static void getAllUsers(HttpEntity<User> entity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
        responseEntity.getHeaders().get("Set-Cookie").stream().forEach(System.out::println);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        System.out.println(httpStatus);
        String users = responseEntity.getBody();
        System.out.println(users);
        responseEntity.getHeaders().get("Set-Cookie");
        HttpHeaders httpHeaders = responseEntity.getHeaders();
        System.out.println(httpHeaders);
    }
    public static void create(HttpEntity<User> entity){
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, entity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        System.out.println(httpStatus);
        String usersNew = responseEntity.getBody();
        System.out.println(usersNew);
        responseEntity.getHeaders().get("Set-Cookie");
        HttpHeaders httpHeaders = responseEntity.getHeaders();
        System.out.println(httpHeaders);
    }

}
