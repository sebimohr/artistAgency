package de.othr.sw.mos.artistAgency.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Event")
public class Event extends EntitySingleLongId {
    @Setter @Getter
    private Long venueId;

    @ManyToOne
    @JoinColumn(name = "artistId")
    @Setter @Getter
    private User artist;
    // TODO: just changed from Long to User -> test if everything has been migrated

    @Setter @Getter
    @Temporal(TemporalType.DATE)
    @DateTimeFormat
    private Date eventDate;
    @Setter @Getter
    private String eventName;

    public Event () { }

    public Event (Long venueId, User artist, Date eventDate, String eventName) {
        this.venueId = venueId;
        this.artist = artist;
        this.eventDate = eventDate;
        this.eventName = eventName;
    }
}
