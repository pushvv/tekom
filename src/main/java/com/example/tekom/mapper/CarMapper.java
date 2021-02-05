package com.example.tekom.mapper;

import com.example.tekom.dto.Car;
import com.example.tekom.entity.BodyType;
import com.example.tekom.entity.CarEntity;
import com.example.tekom.entity.DriveType;
import com.example.tekom.entity.TransmissionType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(imports = {BodyType.class, TransmissionType.class, DriveType.class})
public interface CarMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bodyType", expression = "java(BodyType.valueOf(car.getBodyType().toUpperCase()))")
    @Mapping(target = "transmissionType", expression = "java(TransmissionType.valueOf(car.getTransmissionType().toUpperCase()))")
    @Mapping(target = "driveType", expression = "java(DriveType.valueOf(car.getDriveType().toUpperCase()))")
    CarEntity toEntity(Car car);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bodyType", expression = "java(BodyType.valueOf(car.getBodyType().toUpperCase()))")
    @Mapping(target = "transmissionType", expression = "java(TransmissionType.valueOf(car.getTransmissionType().toUpperCase()))")
    @Mapping(target = "driveType", expression = "java(DriveType.valueOf(car.getDriveType().toUpperCase()))")
    void toExistingEntity(Car car, @MappingTarget CarEntity carEntity);

    Car toDto(CarEntity car);
}
