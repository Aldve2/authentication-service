package yps.systems.ai.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yps.systems.ai.client.model.Person;

@Service
public class PersonService {

    private final RestTemplate restTemplate;

    @Value("${env.url.person-service}")
    private String personServiceUrl;

    @Autowired
    public PersonService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Person getByElementId(String personElementId) {
        String auxURL = personServiceUrl + "/" + personElementId;
        ResponseEntity<Person> response = restTemplate.getForEntity(auxURL, Person.class);
        return response.getBody();
    }

    public Person getByUserElementId(String userElementId) {
        String auxURL = personServiceUrl + "/" + userElementId;
        ResponseEntity<Person> response = restTemplate.getForEntity(auxURL, Person.class);
        return response.getBody();
    }

    public String save(Person person) {
        ResponseEntity<String> response = restTemplate.postForEntity(personServiceUrl, person, String.class);
        return response.getBody();
    }

    public String update(String personElementId, Person person) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Person> entity = new HttpEntity<>(person, headers);
        String auxURL = personServiceUrl + "/" + personElementId;
        ResponseEntity<String> response = restTemplate.exchange(auxURL, HttpMethod.PUT, entity, String.class);
        return response.getBody();
    }

    public String delete(String personElementId) {
        String auxURL = personServiceUrl + "/" + personElementId;
        restTemplate.delete(auxURL);
        return "Person deleted successfully.";
    }

}
