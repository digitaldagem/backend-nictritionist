package com.nictritionist.backend.storage.food_eaten;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodEatenRepository extends JpaRepository<FoodEaten, Long> {
}
