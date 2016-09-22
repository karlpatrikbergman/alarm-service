package se.infinera.metro.service.alarm.controller.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate
@AllArgsConstructor(access = AccessLevel.PUBLIC) //Needed by Orika
@Value
@Builder
public class NodeDTO {
    @NonNull private String ipAddress;
    private int port;
    @NonNull private String userName;
    @NonNull private String password;
}
