package yps.systems.ai.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yps.systems.ai.client.model.Role;

@Service
public class RoleService {

    private final RestTemplate restTemplate;

    @Value("${env.url.role-service}")
    private String roleServiceUrl;

    @Autowired
    public RoleService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Role getByElementId(String elementId) {
        String auxURL = roleServiceUrl + "/" + elementId;
        ResponseEntity<Role> response = restTemplate.getForEntity(auxURL, Role.class);
        return response.getBody();
    }

    public Role getByPersonElementId(String personElementId) {
        String auxURL = roleServiceUrl + "/" + personElementId;
        ResponseEntity<Role> response = restTemplate.getForEntity(auxURL, Role.class);
        return response.getBody();
    }

    public String saveRelationToRole(String roleElementId, String personElementId) {
        String auxURL = roleServiceUrl + "/" + roleElementId + "/" + personElementId;
        ResponseEntity<String> response = restTemplate.postForEntity(auxURL, null, String.class);
        return response.getBody();
    }

}
