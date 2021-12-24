package de.othr.sw.mos.artistAgency.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "FinanceLog")
public class FinanceLog {
    @Id
    @Column(name = "finance_id", nullable = false)
    @Getter
    private Long financeId;

    @Setter @Getter
    private String username;
//    @Setter @Getter
//    private Date moneyReceived;
//    @Setter @Getter
//    private BigDecimal receivedAmount;
//    @Setter @Getter
//    private Date venuePaid;
//    @Setter @Getter
//    private BigDecimal venuePaidAmount;
    @Setter @Getter
    private Date artistPaidDate;
    @Setter @Getter
    private BigDecimal artistPaidAmount;
    @Getter
    final Boolean done = (
//            receivedAmount != null &&
//            venuePaidAmount != null &&
            artistPaidAmount != null);

    public FinanceLog() { }

    public FinanceLog(Long financeId) {
        this.financeId = financeId;
    }

    public FinanceLog(
            Long financeId,
            String username,
//            Date moneyReceived,
//            BigDecimal receivedAmount,
//            Date venuePaid,
//            BigDecimal venuePaidAmount,
            Date artistPaidDate,
            BigDecimal artistPaidAmount
    ) {
        this.financeId = financeId;
        this.username = username;
//        this.moneyReceived = moneyReceived;
//        this.receivedAmount = receivedAmount;
//        this.venuePaid = venuePaid;
//        this.venuePaidAmount = venuePaidAmount;
        this.artistPaidDate = artistPaidDate;
        this.artistPaidAmount = artistPaidAmount;
    }
}
