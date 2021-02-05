package com.example.tekom.service;

import com.example.tekom.dto.Car;
import com.example.tekom.entity.CarEntity;
import com.example.tekom.excel.ExcelExporter;
import com.example.tekom.exception.CarNotFoundException;
import com.example.tekom.mapper.CarMapper;
import com.example.tekom.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final ExcelExporter excelExporter;

    public Long create(Car car) {
        CarEntity carEntity = carMapper.toEntity(car);
        return carRepository.save(carEntity).getId();
    }

    public void delete(Long carId) {
        carRepository.deleteById(carId);
    }

    public Car update(long id, Car car) {
        Optional<CarEntity> toUpdate = carRepository.findById(id);
        CarEntity updated = toUpdate.map(found -> updateCarEntity(car, found))
                .orElseThrow(() -> new CarNotFoundException(id));
        return carMapper.toDto(updated);
    }

    private CarEntity updateCarEntity(Car car, CarEntity found) {
        carMapper.toExistingEntity(car, found);
        return carRepository.save(found);
    }

    public Car get(long id) {
        Optional<CarEntity> requested = carRepository.findById(id);
        return requested.map(carMapper::toDto)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    public Page<Car> find(Specification<CarEntity> spec, Pageable pageable) {
        Page<CarEntity> page = carRepository.findAll(spec, pageable);
        return page.map(carMapper::toDto);
    }

    public ResponseEntity<Resource> export(Specification<CarEntity> spec, Pageable pageable) {
        byte[] bytes = excelExporter.export(spec, pageable);
        ByteArrayResource byteArrayResource = new ByteArrayResource(bytes);
        return ResponseEntity.ok(byteArrayResource);
    }
}
