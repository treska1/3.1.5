package com.example.preproject;

import com.example.preproject.model.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Задача несложная, получаем заголовок(куки) -> сохраняем эти куки и используем их дальше во всех запросах(поочередно POST, PUT, DELETE)
 * с помощью метода .exchange у RestTemplate -> с помощью метода getBody() получаем тело ответа(6 символов) и формируем из него строку для ответа :)
 * Посмотрел видео индуса ссылку на который оставил Денис Четенов и учитывая алгоритм описанный ниже Денисом Шаровым
 * <p>
 * 1. Сделал метод в котором установил тип тела как указано в заголовке headers.setContentType(MediaType.APPLICATION_JSON),
 * вытащил "Set-Cookie" вот таким образом responseEntity.getHeaders().get("Set-Cookie"), который добавил в заголовок вот так
 * headers.set("Cookie", cookies.stream().collect(Collectors.joining(";"))), метод возвращает HttpHeaders.
 * 2. Сделал три метода согласно условию задачки которые возвращают void и в параметре принимают заголовок из пункта 1,
 * этот заголовок нужен будет в методе exchange. Тело из энтити получаем методом getBody().
 */


@SpringBootApplication
public class Application {

    private final static String URL = "http://94.198.50.185:7081/api/users";
    private static RestTemplate restTemplate = new RestTemplate();


//
//    private static final HttpEntity<String> response = restTemplate.getForEntity(URL, String.class);
//    private static final HttpHeaders headers = response.getHeaders();
//    private static final String set_cookie = headers.getFirst(HttpHeaders.SET_COOKIE);

//    public static HttpHeaders getHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Cookie", "JSESSIONID=09A8F0810B37E0A98F2C1F7068C55F4B; Path=/;");
//        return headers;
//    }


    public static void main(String[] args) {

        useRestTemplate();
    }


    public static void useRestTemplate(){
        HttpHeaders headers =new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.get("Cookie");
        HttpEntity<User> request = new HttpEntity<>(headers);
        request.getHeaders();
        showAll(request);

        User user = new User(3L, "James", "Brown", (byte) 10);
        request = new HttpEntity<>(user, headers);
        addUser(request);
        updateUser(request);

    }

    private static void updateUser(HttpEntity<User> request) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, request, String.class);
        System.out.println(responseEntity.getHeaders());
        System.out.println(responseEntity.getBody());
    }

    private static void addUser(HttpEntity<User> request) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, request, String.class);
        System.out.println(responseEntity.getHeaders());
        System.out.println(responseEntity.getBody());
    }

    public static void showAll(HttpEntity<User> request){
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, request, String.class);
        System.out.println(responseEntity.getHeaders());
        System.out.println(responseEntity.getBody());
    }

}


