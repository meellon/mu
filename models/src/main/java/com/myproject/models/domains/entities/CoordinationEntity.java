package com.myproject.models.domains.entities;

import com.myproject.models.domains.enumerations.CoordinationCategory;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@Data
@Entity
@Table(name = "Coordinations")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = false)
public class CoordinationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "brand_id", insertable = false, updatable = false)
    private Integer brandId;

    @Enumerated(EnumType.STRING)
    private CoordinationCategory category;

    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false) // 부모가 존재하지 않으면 자식 생성 불가
    private BrandEntity brand;
}
