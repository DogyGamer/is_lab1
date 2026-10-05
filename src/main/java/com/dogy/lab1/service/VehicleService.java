package com.dogy.lab1.service;

import com.dogy.lab1.dto.VehicleForm;
import com.dogy.lab1.exception.NotFoundException;
import com.dogy.lab1.model.Coordinates;
import com.dogy.lab1.model.FuelType;
import com.dogy.lab1.model.Vehicle;
import com.dogy.lab1.model.VehicleType;
import com.dogy.lab1.repository.CoordinatesRepository;
import com.dogy.lab1.repository.VehicleRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class VehicleService {

    private static final int PAGE_SIZE = 10;
    private static final Set<String> SORT_FIELDS = Set.of("id", "name", "creationDate", "type", "enginePower",
            "numberOfWheels", "capacity", "distanceTravelled", "fuelConsumption", "fuelType");

    private final VehicleRepository vehicleRepository;
    private final CoordinatesRepository coordinatesRepository;
    private final Validator validator;

    public VehicleService(VehicleRepository vehicleRepository, CoordinatesRepository coordinatesRepository,
                          Validator validator) {
        this.vehicleRepository = vehicleRepository;
        this.coordinatesRepository = coordinatesRepository;
        this.validator = validator;
    }

    public Page<Vehicle> getPage(int page, String sort, String direction,
                                 String name, VehicleType type, FuelType fuelType) {
        Vehicle filter = new Vehicle();
        filter.setName(name == null || name.isEmpty() ? null : name);
        filter.setType(type);
        filter.setFuelType(fuelType);

        String field = SORT_FIELDS.contains(sort) ? sort : "id";
        Sort.Direction order = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sorting = Sort.by(order, field).and(Sort.by("id"));

        return vehicleRepository.findAll(Example.of(filter), PageRequest.of(Math.max(page, 0), PAGE_SIZE, sorting));
    }

    public Vehicle get(Integer id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Транспортное средство с id " + id + " не найдено"));
    }

    public Vehicle create(VehicleForm form) {
        return save(new Vehicle(), form);
    }

    public Vehicle update(Integer id, VehicleForm form) {
        return save(get(id), form);
    }

    public void delete(Integer id) {
        vehicleRepository.delete(get(id));
    }

    public Vehicle findMaxId() {
        return getAll().stream()
                .max(Comparator.comparing(Vehicle::getId))
                .orElseThrow(() -> new NotFoundException("Транспортных средств пока нет"));
    }

    public long countFuelTypeGreater(FuelType fuelType) {
        return getAll().stream()
                .filter(v -> v.getFuelType() != null && v.getFuelType().compareTo(fuelType) > 0)
                .count();
    }

    public List<Vehicle> findByNameContaining(String substring) {
        return getAll().stream()
                .filter(v -> v.getName().toLowerCase().contains(substring.toLowerCase()))
                .toList();
    }

    public List<Vehicle> findByType(VehicleType type) {
        return getAll().stream()
                .filter(v -> v.getType() == type)
                .toList();
    }

    public List<Vehicle> findByEnginePower(float min, float max) {
        return getAll().stream()
                .filter(v -> v.getEnginePower() >= min && v.getEnginePower() <= max)
                .toList();
    }

    private List<Vehicle> getAll() {
        return vehicleRepository.findAll(Sort.by("id"));
    }

    private Vehicle save(Vehicle vehicle, VehicleForm form) {
        vehicle.setName(form.name());
        vehicle.setType(form.type());
        vehicle.setEnginePower(form.enginePower());
        vehicle.setNumberOfWheels(form.numberOfWheels());
        vehicle.setCapacity(form.capacity());
        vehicle.setDistanceTravelled(form.distanceTravelled());
        vehicle.setFuelConsumption(form.fuelConsumption());
        vehicle.setFuelType(form.fuelType());
        vehicle.setCoordinates(findOrCreateCoordinates(form));

        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        coordinatesRepository.save(vehicle.getCoordinates());
        return vehicleRepository.save(vehicle);
    }

    private Coordinates findOrCreateCoordinates(VehicleForm form) {
        if (form.coordinatesId() != null) {
            return coordinatesRepository.findById(form.coordinatesId())
                    .orElseThrow(() -> new NotFoundException("Координаты с id " + form.coordinatesId() + " не найдены"));
        }
        Coordinates coordinates = new Coordinates();
        coordinates.setX(form.x());
        coordinates.setY(form.y());
        return coordinates;
    }
}
