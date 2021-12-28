package de.othr.sw.mos.artistAgency.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "FinanceLog")
public class FinanceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long financeId;

    @Setter @Getter
    private Long userId;
    @Setter @Getter
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
        return "ID: " + this.financeId + ", UserId: " + this.userId + ", Date: " + this.artistPaidDate + ", Amount: " + this.artistPaidAmount + ", Done: " + this.done.toString();
    }
}
