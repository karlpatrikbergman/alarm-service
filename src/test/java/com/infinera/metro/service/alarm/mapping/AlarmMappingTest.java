package com.infinera.metro.service.alarm.mapping;

import com.infinera.metro.service.alarm.controller.dto.AlarmDTO;
import com.infinera.metro.service.alarm.service.domain.Alarm;
import com.infinera.metro.service.alarm.util.AlarmMockFactory;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class AlarmMappingTest {
    private MapperFacade mapperFacade; //One alternative
    private BoundMapperFacade<Alarm, AlarmDTO> boundMapperFacade; //Another (faster) alternative
    private Alarm alarmSource;
    private AlarmDTO alarmDTOSource;
    private List<Alarm> alarmList;
    private List<AlarmDTO> alarmDTOList;

    @Before
    public void setup() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFacade = mapperFactory.getMapperFacade();
        boundMapperFacade = mapperFactory.getMapperFacade(Alarm.class, AlarmDTO.class);
        alarmSource = AlarmMockFactory.mockAlarm();
        alarmDTOSource = AlarmMockFactory.mockAlarmDTO();
        alarmList = Collections.singletonList(alarmSource);
        alarmDTOList = Collections.singletonList(alarmDTOSource);
    }

    @Test
    public void alarmToAlarmDto_UsingMapperFacade() {
        AlarmDTO alarmDTO = mapperFacade.map(alarmSource, AlarmDTO.class);
        assertNotNull(alarmDTO);
        assertEquals(alarmSource.getAlarmNeIpAddress(), alarmDTO.getAlarmNeIpAddress());
        log.info(alarmDTO.toString());
    }

    @Test
    public void alarmDtoToAlarm_UsingMapperFacade() {
        Alarm alarm = mapperFacade.map(alarmDTOSource, Alarm.class);
        assertNotNull(alarm);
        assertEquals(alarmDTOSource.getAlarmNeIpAddress(), alarm.getAlarmNeIpAddress());
    }

    @Test
    public void alarmToAlarmDto_UsingBoundMapperFacade() {
        AlarmDTO alarmDTO = boundMapperFacade.map(alarmSource);
        assertNotNull(alarmDTO);
        assertEquals(alarmSource.getAlarmNeIpAddress(), alarmDTO.getAlarmNeIpAddress());
    }

    @Test
    public void alarmDtoToAlarm_UsingBoundMapperFacade() {
        Alarm alarm = boundMapperFacade.mapReverse(alarmDTOSource);
        assertNotNull(alarm);
        assertEquals(alarmDTOSource.getAlarmNeIpAddress(), alarm.getAlarmNeIpAddress());
    }

    @Test
    public void alarmListToAlarmDTOList_UsingMapperFacade() {
        List<AlarmDTO> alarmDTOList = mapperFacade.mapAsList(alarmList, AlarmDTO.class);
        assertNotNull(alarmDTOList);
        assertEquals(1, alarmDTOList.size());
    }

    @Test
    public void alarmDtoListToAlarmList_UsingMapperFacade() {
        List<Alarm> alarmList = mapperFacade.mapAsList(alarmDTOList, Alarm.class);
        assertNotNull(alarmList);
        assertEquals(1, alarmDTOList.size());
    }


}
