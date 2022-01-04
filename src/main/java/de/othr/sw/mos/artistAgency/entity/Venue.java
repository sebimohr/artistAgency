package de.othr.sw.mos.artistAgency.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Venue extends SingleLongIdEntity {

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
}
