package de.othr.sw.mos.artistAgency.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "FinanceLog")
public class FinanceLog extends EntitySingleLongId {
    @Setter @Getter
    private Long userId;
    @Setter @Getter
    @Temporal(TemporalType.DATE)
    @DateTimeFormat
    private Date artistPaidDate;
    @Setter @Getter
    private BigDecimal artistPaidAmount;
    @Getter
    final Boolean done = (
            artistPaidAmount != null);

    public FinanceLog() { }

    public FinanceLog(
            Long username,
            Date artistPaidDate,
            BigDecimal artistPaidAmount
    ) {
        this.userId = username;
        this.artistPaidDate = artistPaidDate;
        this.artistPaidAmount = artistPaidAmount;
    }

    @Override
    public String toString() {
        return "ID: " + this.getID() + ", UserId: " + this.getUserId() + ", Date: " + this.getArtistPaidDate() + ", Amount: " + this.getArtistPaidAmount() + ", Done: " + this.getDone().toString();
    }
}
