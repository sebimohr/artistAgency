package de.othr.sw.mos.artistAgency.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public abstract class SingleLongIdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter @Getter
    private Long ID;

    @Override
    public int hashCode() {
        if (ID == null)
            return 0;
        return ID.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass())
            return false;

        var objectTranslation = (SingleLongIdEntity) o;

        return Objects.equals(ID, objectTranslation.ID);
    }
}
