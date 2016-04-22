package se.infinera.metro.microservice.alarm.service.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate
@AllArgsConstructor(access = AccessLevel.PUBLIC) //Needed by Orika
@Value
@Builder
public class Alarm {
    private String alarmIndex;
    private String alarmObject;
    private String alarmFaultStatus;
    private String alarmMgmtName;
    private String alarmInvPhysIndexOrZero;
    private String alarmInvLogicalIndexOrZero;
    private String alarmType;
    private String alarmCause;
    private String alarmText;
    private String alarmSeverity;
    private String alarmCreatedTime; //": "Tue Apr  5 10:01:45 CEST 2016" --> Change to datetime
    private String alarmLastChangeTime; //": "Tue Apr  5 10:01:45 CEST 2016"  --> Change to datetime
    private String alarmSeqNumber;
    private String alarmNeName;
    private String alarmNeIpAddress;
}
