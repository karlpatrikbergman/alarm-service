package se.infinera.metro.microservice.alarm.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import se.infinera.metro.microservice.alarm.service.domain.Alarm;
import se.infinera.metro.microservice.alarm.service.domain.AlarmPK;

import java.util.List;

@RepositoryRestResource
public interface AlarmRepository extends PagingAndSortingRepository<Alarm, AlarmPK> {
    List<Alarm> findByAlarmNeIpAddress(String alarmNeIpAddress);

}
