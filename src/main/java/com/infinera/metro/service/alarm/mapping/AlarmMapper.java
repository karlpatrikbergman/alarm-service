package com.infinera.metro.service.alarm.mapping;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import com.infinera.metro.service.alarm.controller.dto.AlarmDTO;
import com.infinera.metro.service.alarm.service.domain.Alarm;

import java.util.List;
import java.util.stream.Collectors;

public enum AlarmMapper {
    INSTANCE;
    private static final BoundMapperFacade<Alarm, AlarmDTO> boundMapperFacade;
    private static final MapperFacade mapperFacade;

    static {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
//        mapperFactory.classMap(Alarm.class, AlarmDTO.class)
//                .byDefault().register();
        boundMapperFacade = mapperFactory.getMapperFacade(Alarm.class, AlarmDTO.class);
        mapperFacade = mapperFactory.getMapperFacade();
    }

    public AlarmDTO toAlarmDto(Alarm alarm) {
        return boundMapperFacade.map(alarm);
    }

    public Alarm toAlarm(AlarmDTO alarmDTO) {
        return boundMapperFacade.mapReverse(alarmDTO);
    }

    public List<AlarmDTO> toAlarmDTOList(List<Alarm> alarmList) {
        return mapperFacade.mapAsList(alarmList, AlarmDTO.class);
    }

    public List<Alarm> toAlarmList(List<AlarmDTO> alarmDTOList) {
        return mapperFacade.mapAsList(alarmDTOList, Alarm.class);
    }

    public List<List<AlarmDTO>> toAllNodesAlarmDTOs(List<List<Alarm>> allNodesAlarms) {
        return allNodesAlarms.stream()
                .map(this::toAlarmDTOList)
                .collect(Collectors.toList());
    }

    public List<List<Alarm>> toAllNodesAlarms(List<List<AlarmDTO>> allNodesAlarmDTOs) {
        return allNodesAlarmDTOs.stream()
                .map(this::toAlarmList)
                .collect(Collectors.toList());
    }
}
