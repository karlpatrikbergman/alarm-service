package se.infinera.metro.microservice.alarm.service.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate
@AllArgsConstructor(access = AccessLevel.PUBLIC) //Needed by Orika
@Value
@Builder
public class Foo {
    private String name;
}
