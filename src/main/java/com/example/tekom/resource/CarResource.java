package com.example.tekom.resource;

import com.example.tekom.dto.Car;
import com.example.tekom.entity.CarEntity;
import com.example.tekom.service.CarService;
import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThan;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarResource {

    private final CarService carService;

    @GetMapping("/find")
    public Page<Car> find(
            @And({
                    @Spec(path = "brand", spec = Like.class),
                    @Spec(path = "model", spec = Like.class),
                    @Spec(path = "power", spec = GreaterThan.class),
            }) Specification<CarEntity> spec, Pageable pageable) {
        return carService.find(spec, pageable);
    }

    @GetMapping("/export")
    public ResponseEntity<Resource> export(
            @And({
                    @Spec(path = "brand", spec = Like.class),
                    @Spec(path = "model", spec = Like.class),
                    @Spec(path = "power", spec = GreaterThan.class),
            }) Specification<CarEntity> spec, Pageable pageable) {
        return carService.export(spec, pageable);
    }

    @GetMapping("/{id}")
    public Car get(@PathVariable long id) {
        return carService.get(id);
    }

    @PostMapping
    public Long create(@Validated @RequestBody Car car) {
        return carService.create(car);
    }

    @PutMapping("/{id}")
    public Car update(@PathVariable("id") long id, @Validated @RequestBody Car car) {
        return carService.update(id, car);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        carService.delete(id);
    }

}

