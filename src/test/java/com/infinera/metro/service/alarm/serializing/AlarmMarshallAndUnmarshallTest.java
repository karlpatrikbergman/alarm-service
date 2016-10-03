package com.infinera.metro.service.alarm.serializing;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import com.infinera.metro.service.alarm.service.domain.Alarm;
import com.infinera.metro.service.alarm.util.AlarmMockFactory;
import com.infinera.metro.service.alarm.util.ResourceString;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

@Slf4j
public class AlarmMarshallAndUnmarshallTest {

    private ObjectMapper mapper;

    @Before
    public void setup() {
        mapper = new ObjectMapper();
    }

    @Test //From Java pojo to JSON string == Serialize
    public void marshallAuthor() throws IOException {
        String authorJsonString = mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(AlarmMockFactory.mockAlarm());
        assertNotNull(authorJsonString);
        log.info(authorJsonString);
    }

    @Test //From JSON to Java pojo == Deserialize
    public void unmarshallAuthor() throws IOException {
        String authorJsonString = new ResourceString("serializing/alarm.json").toString();
        assertNotNull(authorJsonString);
        Alarm alarm = mapper.readValue(authorJsonString, Alarm.class);
        assertNotNull(alarm);
        log.info("{}", alarm);
    }
}
