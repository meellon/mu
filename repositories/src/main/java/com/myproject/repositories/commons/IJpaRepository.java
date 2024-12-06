package com.myproject.repositories.commons;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IJpaRepository<T> extends JpaRepository<T, Integer> {
}
