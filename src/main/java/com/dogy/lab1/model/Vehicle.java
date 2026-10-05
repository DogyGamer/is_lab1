package com.dogy.lab1.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "Id должен быть больше нуля")
    @Check(constraints = "id > 0")
    private Integer id;

    @NotBlank(message = "Название не может быть пустым")
    @Check(constraints = "length(trim(name)) > 0")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Координаты не могут быть пустыми")
    @Valid
    @ManyToOne(optional = false)
    @JoinColumn(name = "coordinates_id", nullable = false)
    private Coordinates coordinates;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date creationDate;

    @NotNull(message = "Тип не может быть пустым")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type;

    @NotNull(message = "Мощность двигателя не может быть пустой")
    @Positive(message = "Мощность двигателя должна быть больше 0")
    @Check(constraints = "engine_power > 0")
    @Column(nullable = false)
    private Float enginePower;

    @NotNull(message = "Количество колёс не может быть пустым")
    @Positive(message = "Количество колёс должно быть больше 0")
    @Check(constraints = "number_of_wheels > 0")
    @Column(nullable = false)
    private Integer numberOfWheels;

    @NotNull(message = "Вместимость не может быть пустой")
    @Positive(message = "Вместимость должна быть больше 0")
    @Check(constraints = "capacity > 0")
    @Column(nullable = false)
    private Integer capacity;

    @NotNull(message = "Пройденное расстояние не может быть пустым")
    @Positive(message = "Пройденное расстояние должно быть больше 0")
    @Check(constraints = "distance_travelled > 0")
    @Column(nullable = false)
    private Double distanceTravelled;

    @Positive(message = "Расход топлива должен быть больше 0")
    @Check(constraints = "fuel_consumption > 0")
    private Float fuelConsumption;

    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public Float getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(Float enginePower) {
        this.enginePower = enginePower;
    }

    public Integer getNumberOfWheels() {
        return numberOfWheels;
    }

    public void setNumberOfWheels(Integer numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Double getDistanceTravelled() {
        return distanceTravelled;
    }

    public void setDistanceTravelled(Double distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public Float getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(Float fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }
}
