package com.example.tekom.entity;

import com.example.tekom.excel.ExcelHeader;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "car")
public class CarEntity {

    @Id
    @GeneratedValue
    @ExcelHeader("id")
    @Column(name = "id")
    private Long id;
    @NotBlank(message = "Brand is mandatory")
    @ExcelHeader("Марка")
    @Column(name = "brand")
    private String brand;
    @NotBlank(message = "Model is mandatory")
    @ExcelHeader("Модель")
    @Column(name = "model")
    private String model;
    @Min(value = 1980)
    @Max(value = 2019)
    @ExcelHeader("Год выпуска")
    @Column(name = "prod_year")
    private int prodYear;
    @Min(value = 1)
    @Max(value = 12)
    @ExcelHeader("Месяц выпуска")
    @Column(name = "prod_month")
    private int prodMonth;
    @Min(value = 0)
    @ExcelHeader("Объем двигателя")
    @Column(name = "engine_capacity")
    private int engineCapacity;
    @ExcelHeader("Турбонагнетатель")
    @Column(name = "turbo")
    private boolean turbo;
    @Min(value = 0)
    @ExcelHeader("Мощность")
    @Column(name = "power")
    private int power;
    @Enumerated(EnumType.STRING)
    @ExcelHeader("Трансмиссия")
    @Column(name = "transmission_type")
    private TransmissionType transmissionType;
    @Enumerated(EnumType.STRING)
    @ExcelHeader("Привод")
    @Column(name = "drive_type")
    private DriveType driveType;
    @Enumerated(EnumType.STRING)
    @ExcelHeader("Кузов")
    @Column(name = "body_type")
    private BodyType bodyType;
    @NotBlank
    @ExcelHeader("Цвет")
    @Column(name = "color")
    private String color;
}
