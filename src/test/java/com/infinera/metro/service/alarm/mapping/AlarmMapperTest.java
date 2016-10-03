package com.infinera.metro.service.alarm.mapping;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import com.infinera.metro.service.alarm.controller.dto.AlarmDTO;
import com.infinera.metro.service.alarm.service.domain.Alarm;
import com.infinera.metro.service.alarm.util.AlarmMockFactory;
import com.infinera.metro.service.alarm.util.JsonString;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@Slf4j
public class AlarmMapperTest {
    private AlarmMapper alarmMapper;
    private Alarm alarmSource;
    private AlarmDTO alarmDTOSource;

    @Before
    public void setup() {
        alarmMapper = AlarmMapper.INSTANCE;
        alarmSource = AlarmMockFactory.mockAlarm();
        alarmDTOSource = AlarmMockFactory.mockAlarmDTO();
    }

    @Test
    public void alarmToAlarmDto() {
        AlarmDTO alarmDTO = alarmMapper.toAlarmDto(alarmSource);
        assertNotNull(alarmDTO);
    }

    @Test
    public void alarmDtoToAlarm() {
        Alarm alarm = alarmMapper.toAlarm(alarmDTOSource);
        assertNotNull(alarm);
    }

    @Test
    public void alarmListToAlarmDTOList() {
        List<Alarm> alarmList = AlarmMockFactory.mockAlarmList();
        List<AlarmDTO> alarmDTOList = alarmMapper.toAlarmDTOList(alarmList);
        assertNotNull(alarmDTOList);
        log.info("{}", new JsonString(alarmDTOList));
    }

    @Test
    public void alarmDtoListToAlarmList() {
        List<AlarmDTO> alarmDTOList = AlarmMockFactory.mockAlarmDTOList();
        List<Alarm> alarmList = alarmMapper.toAlarmList(alarmDTOList);
        assertNotNull(alarmList);
        log.info("{}", new JsonString(alarmList));
    }

    @Test
    public void allNodesAlarmsToAllNodesAlarmDTOs() {
        List<List<Alarm>> allNodesAlarms = AlarmMockFactory.mockAllNodesAlarms();
        List<List<AlarmDTO>> allNodesAlarmDTOs = alarmMapper.toAllNodesAlarmDTOs(allNodesAlarms);
        assertNotNull(allNodesAlarmDTOs);
        log.info("{}", allNodesAlarmDTOs);
    }

    @Test
    public void allNodesAlarmDTOsToAllNodesAlarmsList() {
        List<List<Alarm>> allNodesAlarms = AlarmMockFactory.mockAllNodesAlarms();
        List<List<AlarmDTO>> allNodesAlarmDTOs = alarmMapper.toAllNodesAlarmDTOs(allNodesAlarms);
        assertNotNull(allNodesAlarmDTOs);
        log.info("{}", allNodesAlarmDTOs);
    }
}
