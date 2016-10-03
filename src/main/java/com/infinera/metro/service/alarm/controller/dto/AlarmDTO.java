package com.infinera.metro.service.alarm.controller.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate
@AllArgsConstructor(access = AccessLevel.PUBLIC) //Needed by Orika
@Value
@Builder
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AlarmDTO {
//    private long id;
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
