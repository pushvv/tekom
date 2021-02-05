/**
 * @author Vladimir_Pushkarev
 */
package com.example.tekom.dto;

import com.example.tekom.entity.BodyType;
import com.example.tekom.entity.DriveType;
import com.example.tekom.entity.TransmissionType;
import com.example.tekom.validation.ValidEnum;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Car {
    @NotBlank
    private String brand;
    @NotBlank
    private String model;
    @NotNull
    @Min(value = 1980)
    @Max(value = 2019)
    private Integer prodYear;
    @NotNull
    @Min(value = 1)
    @Max(value = 12)
    private Integer prodMonth;
    @NotNull
    @Min(value = 0)
    private Integer engineCapacity;
    private boolean turbo;
    @NotNull
    @Min(value = 0)
    private Integer power;
    @ValidEnum(enumClazz = TransmissionType.class)
    private String transmissionType;
    @ValidEnum(enumClazz = DriveType.class)
    private String driveType;
    @ValidEnum(enumClazz = BodyType.class)
    private String bodyType;
    @NotBlank
    private String color;
}
