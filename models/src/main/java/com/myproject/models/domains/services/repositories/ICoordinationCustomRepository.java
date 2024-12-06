package com.myproject.models.domains.services.repositories;


import com.myproject.models.domains.enumerations.CoordinationCategory;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface ICoordinationCustomRepository<T> {
    List<T> findMinPriceWithEveryCategories();
    List<T> findCheapestBrandAllCategories();

    Optional<T> findMinMaxPriceWithBrandByCategory(CoordinationCategory category, Sort sort);
}
