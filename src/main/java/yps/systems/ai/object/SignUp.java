package yps.systems.ai.object;

import yps.systems.ai.client.model.Person;
import yps.systems.ai.client.model.User;

public record SignUp(Person person, User user, String roleElementId) {

}
