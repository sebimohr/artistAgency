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
    private String name;
    @Setter @Getter
    private BigDecimal cost;
    @Setter @Getter
    private ArtType eventType;

    public Venue() { }

    public Venue(String name, BigDecimal cost, ArtType eventType) {
        this.name = name;
        this.cost = cost;
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "Name: " + this.getName() + ", Kosten: " + this.getCost() + "€, Kapazität: 100";
    }
}
