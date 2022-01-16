package de.othr.sw.mos.artistAgency.entity;

import de.othr.sw.mos.artistAgency.entity.util.EntityLongId;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "FinanceLog")
public class FinanceLog extends EntityLongId {
    @Setter @Getter
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @Setter @Getter
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Setter @Getter
    @Temporal(TemporalType.DATE)
    @DateTimeFormat
    private Date artistPaidDate;
    @Getter
    private BigDecimal artistPaidAmount;

    @Getter
    private Boolean done;

    public FinanceLog() {
        setDone();
    }

    public FinanceLog(User user, Event event) {
        this.user = user;
        this.event = event;
        setDone();
    }

    public void setArtistPaidAmount(BigDecimal artistPaidAmount) {
        this.artistPaidAmount = artistPaidAmount;
        setDone();
    }

    public void setDone() {
        this.done = (artistPaidAmount != null);
    }

    @Override
    public String toString() {
        return "ID: " + this.getID() +
                ", User: " + this.getUser().getUsername() +
                ", Event: " + this.getEvent().getEventName() +
                ", Date: " + this.getArtistPaidDate() +
                ", Amount: " + this.getArtistPaidAmount() +
                ", Done: " + this.getDone().toString();
    }
}
