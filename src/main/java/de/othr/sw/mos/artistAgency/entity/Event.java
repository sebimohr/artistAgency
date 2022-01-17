package de.othr.sw.mos.artistAgency.entity;

import de.othr.sw.mos.artistAgency.entity.util.EntityLongId;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Event")
public class Event extends EntityLongId {
    @Setter @Getter
    private Long venueId;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    @Setter @Getter
    private User artist;

    @Setter @Getter
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventDate;
    @Setter @Getter
    private String eventName;

    public Event () { }

    public Event (Long venueId, User artist, LocalDate eventDate, String eventName) {
        this.venueId = venueId;
        this.artist = artist;
        this.eventDate = eventDate;
        this.eventName = eventName;
    }
}
