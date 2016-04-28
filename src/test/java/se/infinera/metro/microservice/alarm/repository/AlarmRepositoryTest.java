package se.infinera.metro.microservice.alarm.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import se.infinera.metro.microservice.alarm.Application;
import se.infinera.metro.microservice.alarm.HttpConfig;
import se.infinera.metro.microservice.alarm.service.domain.Alarm;
import se.infinera.metro.microservice.alarm.service.domain.AlarmPK;
import se.infinera.metro.microservice.alarm.util.AlarmMockFactory;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example of adding test fixture using an .sql-file, "alarm_repository_test.sql".
 * The file contains sql statements to set db in specific state before test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, HttpConfig.class})
@WebAppConfiguration //Needed when using Swagger for some reason I don't know
@Slf4j
public class AlarmRepositoryTest {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private AlarmRepository alarmRepository;
    @Autowired
    private DataSource ds;
    private static boolean loadDataFixtures = true;

    //Alarm inserted in alarm_repository_test.sql
    private final String ALARM_NE_IP_ADDRESS = "99.99.99.99";
    private final String ALARM_INDEX = "0";

    @Before
    public void loadDataFixtures() {
        if (loadDataFixtures) {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(context.getResource("classpath:/repository/alarm_repository_test.sql"));
            DatabasePopulatorUtils.execute(populator, ds);
            loadDataFixtures = false;
        }
    }

    @Test
    public void saveAlarm() {
        Alarm alarm = AlarmMockFactory.mockAlarm();
        AlarmPK alarmPK = AlarmPK.builder()
                .alarmIndex(alarm.getAlarmIndex())
                .alarmNeIpAddress(alarm.getAlarmNeIpAddress())
                .build();
        alarmRepository.save(alarm);
        assertTrue(alarmRepository.exists(alarmPK));
    }

    @Test
    public void getAlarm() {
        AlarmPK alarmPK = AlarmPK.builder()
                .alarmIndex(ALARM_INDEX)
                .alarmNeIpAddress(ALARM_NE_IP_ADDRESS)
                .build();
        Alarm alarm = alarmRepository.findOne(alarmPK);
        assertNotNull(alarm);
    }

    @Test
    public void updateAlarm() {
        final long totalNumberOfAlarms = alarmRepository.count();
        AlarmPK alarmPK = AlarmPK.builder()
                .alarmIndex(ALARM_INDEX)
                .alarmNeIpAddress(ALARM_NE_IP_ADDRESS)
                .build();
        Alarm alarm = alarmRepository.findOne(alarmPK);
        assertNotNull(alarm);
        Alarm updatedAlarm = Alarm.builder()
                .alarmIndex(alarm.getAlarmIndex())
                .alarmObject(alarm.getAlarmObject())
                .alarmFaultStatus(alarm.getAlarmFaultStatus())
                .alarmMgmtName(alarm.getAlarmMgmtName())
                .alarmInvPhysIndexOrZero(alarm.getAlarmInvPhysIndexOrZero())
                .alarmInvLogicalIndexOrZero(alarm.getAlarmInvLogicalIndexOrZero())
                .alarmType(alarm.getAlarmType())
                .alarmCause(alarm.getAlarmCause())
                .alarmText("some new alarm text")
                .alarmSeverity(alarm.getAlarmSeverity())
                .alarmCreatedTime(alarm.getAlarmCreatedTime())
                .alarmLastChangeTime(alarm.getAlarmLastChangeTime())
                .alarmSeqNumber(alarm.getAlarmSeqNumber())
                .alarmNeName(alarm.getAlarmNeName())
                .alarmNeIpAddress(alarm.getAlarmNeIpAddress())
                .build();

        alarmRepository.save(updatedAlarm);
        assertEquals(totalNumberOfAlarms, alarmRepository.count());
        Alarm findItAgain = alarmRepository.findOne(alarmPK);
        assertEquals("some new alarm text", findItAgain.getAlarmText());
    }

    @Test
    public void findByAlarmNeIpAddress() {
        List<Alarm> alarmList = alarmRepository.findByAlarmNeIpAddress(ALARM_NE_IP_ADDRESS);
        assertEquals(1, alarmList.size());
        Alarm alarm = alarmList.get(0);
        assertNotNull(alarm);
        assertEquals("thin white duke", alarm.getAlarmNeName());
    }
}
