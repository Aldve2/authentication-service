package yps.systems.ai.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import yps.systems.ai.client.model.Session;

@Service
public class SessionService {

    private final RestTemplate restTemplate;

    @Value("${env.url.session-service}")
    private String sessionServiceUrl;

    @Autowired
    public SessionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Session getById(@PathVariable String id) {
        String auxURL = sessionServiceUrl + "/" + id;
        ResponseEntity<Session> response = restTemplate.getForEntity(auxURL, Session.class);
        return response.getBody();
    }

    public Session save(Session session) {
        ResponseEntity<Session> response = restTemplate.postForEntity(sessionServiceUrl, session, Session.class);
        return response.getBody();
    }

    public void delete(String id) {
        String auxURL = sessionServiceUrl + "/" + id;
        restTemplate.delete(auxURL);
    }

}
