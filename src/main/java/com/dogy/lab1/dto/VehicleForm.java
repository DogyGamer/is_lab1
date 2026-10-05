package com.dogy.lab1.dto;

import com.dogy.lab1.model.FuelType;
import com.dogy.lab1.model.VehicleType;

public record VehicleForm(
        String name,
        VehicleType type,
        Float enginePower,
        Integer numberOfWheels,
        Integer capacity,
        Double distanceTravelled,
        Float fuelConsumption,
        FuelType fuelType,
        Integer coordinatesId,
        Double x,
        Double y) {
}
