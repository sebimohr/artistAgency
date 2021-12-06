package de.othr.sw.mos.artistAgency.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Event {
    @Id
    @Column(name = "event_id", nullable = false)
    private Long eventId;

    private int venueId;
    private Date date;
    private String eventName;

    public Event () {
        // TODO: Implement constructor
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Long getEventId() {
        return eventId;
    }

    public int getVenueId() {
        return venueId;
    }

    public Date getDate() {
        return date;
    }

    public String getEventName() {
        return eventName;
    }
}
