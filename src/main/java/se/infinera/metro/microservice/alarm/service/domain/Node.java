package se.infinera.metro.microservice.alarm.service.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate
@AllArgsConstructor(access = AccessLevel.PUBLIC) //Needed by Orika
@Value
@Builder
@Entity
public class Node {
    @NonNull
    @Id
    private final String ipAddress;
    private final int port;
    @NonNull
    private final String userName;
    @NonNull
    private final String password;
}
