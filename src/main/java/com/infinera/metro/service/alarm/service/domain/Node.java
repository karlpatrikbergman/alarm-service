package com.infinera.metro.service.alarm.service.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * I would like to get behaviour like getAlarms() into this
 * class, but I run into trouble with Orika and Lombok.
 *
 * To retrieve alarms I need an autowired field "AlarmRepository.
 * I don't want to include this field when mapping to/from
 * Node <--> NodeDTO. Orika expects a constructor with the
 * fields that are to be mapped (probably because Node is
 * immutable, and has no setters a specific constructor is
 * the only way to handle this.
 *
 * I don't know how to tell Lombok to auto-generate a constructor
 * that omits a field that is not initialized (but instead autowired).
 *
 */
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
