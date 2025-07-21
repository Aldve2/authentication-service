package yps.systems.ai.client.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
public class Person {

    private String elementId;
    private String firstName;
    private String lastName;
    private String identification;
    private String email;
    private String phone;

}

