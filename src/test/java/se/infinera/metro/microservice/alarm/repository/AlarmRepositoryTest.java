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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

    @Before
    public void loadDataFixtures() {
        if (loadDataFixtures) {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(context.getResource("classpath:/testdata.sql"));
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
                .alarmIndex("0")
                .alarmNeIpAddress("99.99.99.99")
                .build();
        Alarm alarm = alarmRepository.findOne(alarmPK);
        assertNotNull(alarm);
    }

    @Test
    public void updateAlarm() {
        final long totalNumberOfAlarms = alarmRepository.count();
        AlarmPK alarmPK = AlarmPK.builder()
                .alarmIndex("0")
                .alarmNeIpAddress("99.99.99.99")
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
    }
}
