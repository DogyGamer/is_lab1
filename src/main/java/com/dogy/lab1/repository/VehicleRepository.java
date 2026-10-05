package com.dogy.lab1.repository;

import com.dogy.lab1.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    boolean existsByCoordinatesId(Integer coordinatesId);
}
