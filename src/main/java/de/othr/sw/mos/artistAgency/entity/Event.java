package de.othr.sw.mos.artistAgency.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long eventId;

    @Setter @Getter
    private int venueId;
    @Setter @Getter
    private int artistId;
    @Setter @Getter
    private Date eventDate;
    @Setter @Getter
    private String eventName;

    public Event () { }

    public Event (int venueId, int artistId, Date eventDate, String eventName) {
        this.venueId = venueId;
        this.artistId = artistId;
        this.eventDate = eventDate;
        this.eventName = eventName;
    }
}
