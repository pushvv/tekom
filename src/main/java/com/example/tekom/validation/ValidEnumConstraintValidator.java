package com.example.tekom.validation;

import static java.util.stream.Collectors.toList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class ValidEnumConstraintValidator implements ConstraintValidator<ValidEnum, String> {
    private List<String> valueList;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return valueList.contains(value.toUpperCase());
    }

    @Override
    public void initialize(ValidEnum validEnum) {
        valueList = Arrays.stream(validEnum.enumClazz().getEnumConstants())
                .map(Enum::name)
                .map(String::toUpperCase)
                .collect(toList());
    }
}
