package io.github.cadnunsdimir.congespapi.infra.data.entities.congregation;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

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
