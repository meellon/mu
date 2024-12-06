package com.myproject.repositories.domains.impls;

import com.myproject.models.components.MessageComponent;
import com.myproject.models.domains.entities.CoordinationEntity;
import com.myproject.models.domains.entities.QCoordinationEntity;
import com.myproject.models.domains.enumerations.CoordinationCategory;
import com.myproject.repositories.domains.interfaces.ICoordinationJpaRepository;
import com.myproject.repositories.domains.interfaces.ICoordinationRepositoryDSLCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.myproject.models.domains.entities.QCoordinationEntity.coordinationEntity;

@Repository
public class CoordinationRepositoryDSLCustomImpl extends QuerydslRepositorySupport implements ICoordinationRepositoryDSLCustom {

    private final JPAQueryFactory jpaQueryFactory;
    @PersistenceContext
    private EntityManager entityManager;

    private final ICoordinationJpaRepository iJpaRepository;

    public CoordinationRepositoryDSLCustomImpl(JPAQueryFactory jpaQueryFactory, ICoordinationJpaRepository iJpaRepository) {
        super(CoordinationEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
        this.iJpaRepository = iJpaRepository;
    }

    @Override
    public Page<CoordinationEntity> findByAllQueryDSL() {
        // TODO:: 현재는 구현 하지 않음
        return null;
    }

    @Override
    public Optional<CoordinationEntity> findById(int id) {
        return Optional.of(
                Objects.requireNonNull(jpaQueryFactory
                        .selectFrom(coordinationEntity)
                        .where(coordinationEntity.id.eq(id))
                        .fetchFirst())
        );
    }

    @Override
    public long count() {
        // TODO:: 현재는 구현 하지 않음
        return 0;
    }

    @Override
    public CoordinationEntity save(CoordinationEntity entity) {
        return iJpaRepository.save(entity);
    }

    @Override
    public void deleteById(int id) {
        iJpaRepository.deleteById(id);
    }

    @Override
    public List<CoordinationEntity> findMinPriceWithEveryCategories() {
        QCoordinationEntity subCoordinationEntity1 = new QCoordinationEntity("subCoordinationEntity1");
        QCoordinationEntity subCoordinationEntity2 = new QCoordinationEntity("subCoordinationEntity2");

        JPQLQuery<Integer> subQuery = JPAExpressions
                .select(subCoordinationEntity1.id.min())
                .from(subCoordinationEntity1)
                .where(subCoordinationEntity1.price.eq(
                        JPAExpressions
                                .select(subCoordinationEntity2.price.min())
                                .from(subCoordinationEntity2)
                                .where(subCoordinationEntity2.category.eq(subCoordinationEntity1.category))
                ))
                .groupBy(subCoordinationEntity1.category);

        return jpaQueryFactory
                .selectFrom(coordinationEntity)
                .where(coordinationEntity.id.in(subQuery))
                .orderBy(coordinationEntity.category.asc())
                .fetch();
    }

    @Override
    public List<CoordinationEntity> findCheapestBrandAllCategories() {
        List<Tuple> brandWithPrices = jpaQueryFactory
                .select(
                        coordinationEntity.brand.name, // 변경된 구조를 반영 (Brand의 이름)
                        coordinationEntity.price.sum().as("totalPrice")
                )
                .from(coordinationEntity)
                .groupBy(coordinationEntity.brand)
                .having(
                        coordinationEntity.category.countDistinct()
                                .eq(jpaQueryFactory
                                        .select(coordinationEntity.category.countDistinct())
                                        .from(coordinationEntity)
                                )
                )
                .orderBy(Expressions.numberPath(Integer.class, "totalPrice").asc())
                .limit(1)
                .fetch();

        if (brandWithPrices.isEmpty()) {
            throw new IllegalArgumentException(MessageComponent.MESSAGE.getMessage("Service.NOT_FOUND"));
        }

        String cheapestBrandName = brandWithPrices.get(0).get(coordinationEntity.brand.name);

        return jpaQueryFactory
                .selectFrom(coordinationEntity)
                .where(coordinationEntity.brand.name.eq(cheapestBrandName))
                .orderBy(coordinationEntity.category.asc())
                .fetch();
    }

    @Override
    public Optional<CoordinationEntity> findMinMaxPriceWithBrandByCategory(CoordinationCategory category, Sort sort) {
        return Optional.of(
                Objects.requireNonNull(Objects.requireNonNull(getQuerydsl())
                        .applySorting(sort, from(coordinationEntity)
                                .where(coordinationEntity.category.eq(category)))
                        .fetchFirst())
        );
    }
}
