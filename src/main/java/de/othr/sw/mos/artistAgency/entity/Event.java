package de.othr.sw.mos.artistAgency.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Event")
public class Event extends SingleLongIdEntity {
    @Setter @Getter
    private Long venueId;
    @Setter @Getter
    private Long artistId;
    @Setter @Getter
    @Temporal(TemporalType.DATE)
    @DateTimeFormat
    private Date eventDate;
    @Setter @Getter
    private String eventName;

    public Event () { }

    public Event (Long venueId, Long artistId, Date eventDate, String eventName) {
        this.venueId = venueId;
        this.artistId = artistId;
        this.eventDate = eventDate;
        this.eventName = eventName;
    }
}
