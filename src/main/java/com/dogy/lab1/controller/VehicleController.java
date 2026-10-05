package com.dogy.lab1.controller;

import com.dogy.lab1.dto.VehicleForm;
import com.dogy.lab1.model.FuelType;
import com.dogy.lab1.model.Vehicle;
import com.dogy.lab1.model.VehicleType;
import com.dogy.lab1.service.VehicleService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public Map<String, Object> getPage(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "id") String sort,
                                       @RequestParam(defaultValue = "asc") String direction,
                                       @RequestParam(required = false) String name,
                                       @RequestParam(required = false) VehicleType type,
                                       @RequestParam(required = false) FuelType fuelType) {
        Page<Vehicle> result = vehicleService.getPage(page, sort, direction, name, type, fuelType);
        return Map.of("items", result.getContent(), "totalPages", result.getTotalPages());
    }

    @GetMapping("/{id}")
    public Vehicle get(@PathVariable Integer id) {
        return vehicleService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicle create(@RequestBody VehicleForm form) {
        return vehicleService.create(form);
    }

    @PutMapping("/{id}")
    public Vehicle update(@PathVariable Integer id, @RequestBody VehicleForm form) {
        return vehicleService.update(id, form);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        vehicleService.delete(id);
    }

    @GetMapping("/max-id")
    public Vehicle maxId() {
        return vehicleService.findMaxId();
    }

    @GetMapping("/count-fuel-type-greater")
    public Map<String, Long> countFuelTypeGreater(@RequestParam FuelType fuelType) {
        return Map.of("count", vehicleService.countFuelTypeGreater(fuelType));
    }

    @GetMapping("/by-name")
    public List<Vehicle> byName(@RequestParam String substring) {
        return vehicleService.findByNameContaining(substring);
    }

    @GetMapping("/by-type")
    public List<Vehicle> byType(@RequestParam VehicleType type) {
        return vehicleService.findByType(type);
    }

    @GetMapping("/by-engine-power")
    public List<Vehicle> byEnginePower(@RequestParam float min, @RequestParam float max) {
        return vehicleService.findByEnginePower(min, max);
    }
}
