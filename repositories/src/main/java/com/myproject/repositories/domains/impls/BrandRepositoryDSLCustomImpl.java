package com.myproject.repositories.domains.impls;

import com.myproject.models.domains.entities.BrandEntity;
import com.myproject.repositories.domains.interfaces.IBrandJpaRepository;
import com.myproject.repositories.domains.interfaces.IBrandRepositoryDSLCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

import static com.myproject.models.domains.entities.QBrandEntity.brandEntity;

@Repository
public class BrandRepositoryDSLCustomImpl extends QuerydslRepositorySupport implements IBrandRepositoryDSLCustom {

    private final JPAQueryFactory jpaQueryFactory;
    @PersistenceContext
    private EntityManager entityManager;

    private final IBrandJpaRepository iJpaRepository;

    public BrandRepositoryDSLCustomImpl(JPAQueryFactory jpaQueryFactory, IBrandJpaRepository iJpaRepository) {
        super(BrandEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
        this.iJpaRepository = iJpaRepository;
    }

    @Override
    public Page<BrandEntity> findByAllQueryDSL() {
        // TODO:: 현재는 구현 하지 않음
        return null;
    }

    @Override
    public Optional<BrandEntity> findById(int id) {
        return Optional.of(
                Objects.requireNonNull(jpaQueryFactory
                        .selectFrom(brandEntity)
                        .where(brandEntity.id.eq(id))
                        .fetchFirst())
        );
    }

    @Override
    public long count() {
        // TODO:: 현재는 구현 하지 않음
        return 0;
    }

    @Override
    public BrandEntity save(BrandEntity entity) {
        return iJpaRepository.save(entity);
    }

    @Override
    public void deleteById(int id) {
        iJpaRepository.deleteById(id);
    }
}
