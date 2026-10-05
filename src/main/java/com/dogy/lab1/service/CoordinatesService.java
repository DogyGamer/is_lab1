package com.dogy.lab1.service;

import com.dogy.lab1.exception.ConflictException;
import com.dogy.lab1.exception.NotFoundException;
import com.dogy.lab1.model.Coordinates;
import com.dogy.lab1.repository.CoordinatesRepository;
import com.dogy.lab1.repository.VehicleRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CoordinatesService {

    private final CoordinatesRepository coordinatesRepository;
    private final VehicleRepository vehicleRepository;

    public CoordinatesService(CoordinatesRepository coordinatesRepository, VehicleRepository vehicleRepository) {
        this.coordinatesRepository = coordinatesRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<Coordinates> getAll() {
        return coordinatesRepository.findAll(Sort.by("id"));
    }

    public void delete(Integer id) {
        Coordinates coordinates = coordinatesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Координаты с id " + id + " не найдены"));
        if (vehicleRepository.existsByCoordinatesId(id)) {
            throw new ConflictException("Нельзя удалить координаты " + id + ": они используются транспортным средством");
        }
        coordinatesRepository.delete(coordinates);
    }
}
