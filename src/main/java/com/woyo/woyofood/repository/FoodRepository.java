package com.woyo.woyofood.repository;

import com.woyo.woyofood.model.food.FoodModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FoodRepository extends JpaRepository<FoodModel, Integer> {

    @Query(value = "SELECT * FROM tab_food " +
            "WHERE price >= ?1 " +
            "AND price <= ?2 ", nativeQuery = true)
    List<FoodModel> filterByPrice(int min, int max);
}
