package se.infinera.metro.microservice.alarm.controller.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate
@AllArgsConstructor(access = AccessLevel.PUBLIC) //Needed by Orika
@Value
@Builder
public class NodeDTO {
    private String ipAddress;
    private int port;
    private String userName;
    private String password;
}
