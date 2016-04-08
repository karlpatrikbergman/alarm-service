package se.infinera.metro.microservice.alarm.service.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeJsonMappingException("Failed to convert to pretty format json string");
        }
    }
}
