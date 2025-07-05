package oi.github.cadnunsdimir.congespapi.entities.congregation;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name= "congregation_group")
public class CongregationGroup {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(unique = true, nullable = false, length = 15)
    private String groupOverseer;

    @Column(unique = true, nullable = false)
    private int groupNumber;
}
