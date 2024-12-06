package com.myproject.models.domains.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Builder
@Data
@Entity
@Table(name = "Brands")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = false)
public class BrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoordinationEntity> coordinations = new ArrayList<>();

    public void addCoordination(CoordinationEntity coordination) {
        coordination.setBrand(this);
        this.coordinations.add(coordination);
    }

    public void removeCoordination(CoordinationEntity coordination) {
        this.coordinations.remove(coordination);
        coordination.setBrand(null);
    }
}
