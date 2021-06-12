package com.nictritionist.backend.storage.nutrient_eaten;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NutrientEatenRepository extends JpaRepository<NutrientEaten, Long> {
}
