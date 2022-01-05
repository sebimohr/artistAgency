package de.othr.sw.mos.artistAgency.entity;

import de.othr.sw.mos.artistAgency.entity.util.ArtType;
import de.othr.sw.mos.artistAgency.entity.util.EntityLongId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Venue extends EntityLongId {

    @Setter @Getter
    private String venueName;
    @Setter @Getter
    private BigDecimal cost;
    @Setter @Getter
    private ArtType artType;

    public Venue() { }

    public Venue(String name, BigDecimal cost, ArtType artType) {
        this.venueName = name;
        this.cost = cost;
        this.artType = artType;
    }

    @Override
    public String toString() {
        return "Name: " + this.getVenueName() + ", Kosten: " + this.getCost() + "€, Kapazität: 100";
    }
}
