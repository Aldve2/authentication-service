package yps.systems.ai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import yps.systems.ai.client.model.Person;
import yps.systems.ai.client.model.Role;
import yps.systems.ai.client.model.Session;
import yps.systems.ai.client.service.PersonService;
import yps.systems.ai.client.service.RoleService;
import yps.systems.ai.client.model.User;
import yps.systems.ai.client.service.SessionService;
import yps.systems.ai.client.service.UserService;
import yps.systems.ai.object.SignIn;
import yps.systems.ai.object.SignUp;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    private final PersonService personService;
    private final UserService userService;
    private final RoleService roleService;
    private final SessionService sessionService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationService(PersonService personService, UserService userService, RoleService roleService, SessionService sessionService, JwtUtil jwtUtil) {
        this.personService = personService;
        this.userService = userService;
        this.roleService = roleService;
        this.sessionService = sessionService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public ResponseEntity<String> signUp(SignUp signUp) {
        String encodedPassword = passwordEncoder.encode(signUp.user().getPassword());
        User user = signUp.user();
        user.setPassword(encodedPassword);
        String personElementId = personService.save(signUp.person());
        userService.save(user);
        roleService.saveRelationToRole(signUp.roleElementId(), personElementId);
        return ResponseEntity.ok("User signed up successfully.");
    }

    public ResponseEntity<String> signIn(SignIn signIn) {
        User user = userService.getByUsername(signIn.username());
        if (!passwordEncoder.matches(signIn.password(), user.getPassword())) {
            return ResponseEntity.status(401).body("Credenciales inválidas.");
        }
        Person person = personService.getByUserElementId(user.getElementId());
        Role role = roleService.getByPersonElementId(person.getElementId());
        Map<String, Object> claims = new HashMap<>();
        claims.put("roleElementId", role.getElementId());
        claims.put("username", user.getUsername());
        claims.put("email", person.getEmail());
        String token = jwtUtil.generateToken(claims);
        Session session = sessionService.save(new Session("", person.getElementId(), token));
        return ResponseEntity.ok(session.getId());
    }
}
