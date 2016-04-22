package se.infinera.metro.microservice.alarm.mapping;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.Before;
import org.junit.Test;
import se.infinera.metro.microservice.alarm.controller.dto.FooDTO;
import se.infinera.metro.microservice.alarm.service.domain.Foo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FooMapperTest {
    private MapperFacade mapperFacade; //One alternative
    private BoundMapperFacade<Foo, FooDTO> boundMapperFacade; //Another (faster) alternative
    private Foo fooSource;
    private FooDTO fooDtoSource;
    private static final String EXPECTED_NAME = "Guns And Roses";

    @Before
    public void setup() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFacade = mapperFactory.getMapperFacade();
        boundMapperFacade = mapperFactory.getMapperFacade(Foo.class, FooDTO.class);
        fooSource = fooSource.builder()
                .name(EXPECTED_NAME)
                .build();
        fooDtoSource = fooDtoSource.builder()
                .name(EXPECTED_NAME)
                .build();
    }

    @Test
    public void fooToFooDTO_UsingMapperFacade() {
        FooDTO fooDTO = mapperFacade.map(fooSource, FooDTO.class);
        assertNotNull(fooDTO);
        assertEquals(EXPECTED_NAME, fooDTO.getName());
    }

    @Test
    public void fooDtoToFoo_UsingMapperFacade() {
        Foo foo = mapperFacade.map(fooDtoSource, Foo.class);
        assertNotNull(foo);
        assertEquals(EXPECTED_NAME, foo.getName());
    }

    @Test
    public void fooToFooDTO_UsingBoundMapperFacade() {
        FooDTO fooDTO = boundMapperFacade.map(fooSource);
        assertNotNull(fooDTO);
        assertEquals(EXPECTED_NAME, fooDTO.getName());
    }

    @Test
    public void fooDtoToFoo_UsingBoundMapperFacade() {
        Foo foo = boundMapperFacade.mapReverse(fooDtoSource);
        assertNotNull(foo);
        assertEquals(EXPECTED_NAME, foo.getName());
    }
}
