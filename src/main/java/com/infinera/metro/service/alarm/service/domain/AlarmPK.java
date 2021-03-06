package com.infinera.metro.service.alarm.service.domain;

import lombok.*;

import java.io.Serializable;

/**
 * There are some rules that your PK class should follow:
 *  It must have a default constructor without arguments.
 *  It must implement the java.io.Serializable interface.
 *  It must override the methods equals and hashCode.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate
@AllArgsConstructor(access = AccessLevel.PRIVATE) //Needed by builder?
@Value
@Builder
public class AlarmPK implements Serializable {
    private static final long serialVersionUID = 7689587410619336611L;
    private final String alarmNeIpAddress;
    private final String alarmIndex;
}
