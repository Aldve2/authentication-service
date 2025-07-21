package yps.systems.ai.client.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    private String id;
    private String personElementId;
    private String jwt;

}
