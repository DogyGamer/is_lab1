package com.dogy.lab1.repository;

import com.dogy.lab1.model.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordinatesRepository extends JpaRepository<Coordinates, Integer> {
}
