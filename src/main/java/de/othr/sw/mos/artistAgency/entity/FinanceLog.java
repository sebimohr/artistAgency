package de.othr.sw.mos.artistAgency.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class FinanceLog {
    @Id
    @Column(name = "finance_id", nullable = false)
    private Long financeId;

    private Date moneyReceived;
    private BigDecimal receivedAmount;
    private Date venuePaid;
    private BigDecimal venuePaidAmount;
    private Date artistPaid;
    private BigDecimal artistPaidAmount;
    private Boolean done;

    public FinanceLog() {
        // TODO: implement constructor
    }

    public void setFinanceId(Long financeId) {
        this.financeId = financeId;
    }

    public void setMoneyReceived(Date moneyReceived) {
        this.moneyReceived = moneyReceived;
    }

    public void setReceivedAmount(BigDecimal receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public void setVenuePaid(Date venuePaid) {
        this.venuePaid = venuePaid;
    }

    public void setVenuePaidAmount(BigDecimal venuePaidAmount) {
        this.venuePaidAmount = venuePaidAmount;
    }

    public void setArtistPaid(Date artistPaid) {
        this.artistPaid = artistPaid;
    }

    public void setArtistPaidAmount(BigDecimal artistPaidAmount) {
        this.artistPaidAmount = artistPaidAmount;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Long getFinanceId() {
        return financeId;
    }

    public Date getMoneyReceived() {
        return moneyReceived;
    }

    public BigDecimal getReceivedAmount() {
        return receivedAmount;
    }

    public Date getVenuePaid() {
        return venuePaid;
    }

    public BigDecimal getVenuePaidAmount() {
        return venuePaidAmount;
    }

    public Date getArtistPaid() {
        return artistPaid;
    }

    public BigDecimal getArtistPaidAmount() {
        return artistPaidAmount;
    }

    public Boolean getDone() {
        return done;
    }
}
