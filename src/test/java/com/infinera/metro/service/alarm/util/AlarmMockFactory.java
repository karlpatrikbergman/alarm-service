package com.infinera.metro.service.alarm.util;

import com.infinera.metro.service.alarm.controller.dto.AlarmDTO;
import com.infinera.metro.service.alarm.service.domain.Alarm;
import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.Arrays;
import java.util.List;

public final class AlarmMockFactory {
    private static final RandomIpGenerator ipGenerator = RandomIpGenerator.INSTANCE;
    public static Alarm mockAlarm() {
        return Alarm.builder()
                .alarmIndex(Integer.toString(new RandomDataGenerator().nextInt(0,99)))
                .alarmObject("alarm-object")
                .alarmFaultStatus("alarm-faultstatus")
                .alarmMgmtName("alarm-mgmtname")
                .alarmInvPhysIndexOrZero("alarm-inv-phys-index-or-zero")
                .alarmInvLogicalIndexOrZero("alarm-inv-logical-index-or-zero")
                .alarmType("alarm-type")
                .alarmCause("alarm-type")
                .alarmText("alarm-text")
                .alarmSeverity("alarm-severity")
                .alarmCreatedTime("alarm-created-time")
                .alarmLastChangeTime("alarm-last-change-time")
                .alarmSeqNumber("alarm-number")
                .alarmNeName("alarm-ne-name")
                .alarmNeIpAddress(ipGenerator.getRandomIpAddress())
                .build();
    }

    public static AlarmDTO mockAlarmDTO() {
        return AlarmDTO.builder()
                .alarmIndex(Integer.toString(new RandomDataGenerator().nextInt(0,99)))
                .alarmObject("alarm-object")
                .alarmFaultStatus("alarm-faultstatus")
                .alarmMgmtName("alarm-mgmtname")
                .alarmInvPhysIndexOrZero("alarm-inv-phys-index-or-zero")
                .alarmInvLogicalIndexOrZero("alarm-inv-logical-index-or-zero")
                .alarmType("alarm-type")
                .alarmCause("alarm-type")
                .alarmText("alarm-text")
                .alarmSeverity("alarm-severity")
                .alarmCreatedTime("alarm-created-time")
                .alarmLastChangeTime("alarm-last-change-time")
                .alarmSeqNumber("alarm-number")
                .alarmNeName("alarm-ne-name")
                .alarmNeIpAddress(ipGenerator.getRandomIpAddress())
                .build();
    }

    public static List<AlarmDTO> mockAlarmDTOList() {
        return Arrays.asList(mockAlarmDTO());
    }

    public static List<Alarm> mockAlarmList() {
        return Arrays.asList(mockAlarm());
    }

    public static List<List<AlarmDTO>> mockAllNodesAlarmDTOs() {
        return Arrays.asList(mockAlarmDTOList());
    }

    public static List<List<Alarm>> mockAllNodesAlarms() {
        return Arrays.asList(mockAlarmList());
    }
}
