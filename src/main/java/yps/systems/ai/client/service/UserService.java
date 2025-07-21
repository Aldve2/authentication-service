package yps.systems.ai.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yps.systems.ai.client.model.User;

@Service
public class UserService {

    private final RestTemplate restTemplate;

    @Value("${env.url.user-service}")
    private String userServiceUrl;

    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public User getByElementId(String elementId) {
        String auxURL = userServiceUrl + "/" + elementId;
        ResponseEntity<User> response = restTemplate.getForEntity(auxURL, User.class);
        return response.getBody();
    }

    public User getByUsername(String username) {
        String auxURL = userServiceUrl + "/" + username;
        ResponseEntity<User> response = restTemplate.getForEntity(auxURL, User.class);
        return response.getBody();
    }

    public String save(User user) {
        ResponseEntity<String> response = restTemplate.postForEntity(userServiceUrl, user, String.class);
        return response.getBody();
    }

    public String update(String elementId, User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        String auxURL = userServiceUrl + "/" + elementId;
        ResponseEntity<String> response = restTemplate.exchange(auxURL, HttpMethod.PUT, entity, String.class);
        return response.getBody();
    }

    public String delete(String elementId) {
        String auxURL = userServiceUrl + "/" + elementId;
        restTemplate.delete(auxURL);
        return "User deleted successfully.";
    }

}
