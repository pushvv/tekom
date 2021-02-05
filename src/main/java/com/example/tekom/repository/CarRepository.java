package com.example.tekom.repository;

import com.example.tekom.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CarRepository extends PagingAndSortingRepository<CarEntity, Long>, JpaSpecificationExecutor<CarEntity> {
}
