package se.infinera.metro.microservice.alarm.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Value
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Alarm {
    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne
    private Node node;
}
