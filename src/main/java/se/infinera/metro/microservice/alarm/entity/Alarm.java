package se.infinera.metro.microservice.alarm.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Value
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Alarm {
    @Id
    @GeneratedValue
    private Long id;
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

    @ManyToOne
    private Node node;
}
