/**
 * @author Vladimir_Pushkarev
 */
package com.example.tekom.excel;

import com.example.tekom.entity.CarEntity;
import com.example.tekom.exception.CarServiceGeneralException;
import com.example.tekom.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelExporter {

    private final CarRepository carRepository;

    public byte[] export(Specification<CarEntity> spec, Pageable pageable) {
        List<CarEntity> cars = carRepository.findAll(spec, pageable).getContent();

        try (HSSFWorkbook workbook = new HSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            HSSFSheet sheet = workbook.createSheet("Cars");
            List<Field> fields = findAnnotatedFields();
            createHeader(sheet, fields);
            createRows(cars, sheet, fields);
            autoSizeColumns(sheet, fields);
            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            log.error("Should never happen");
        }
        throw new CarServiceGeneralException();
    }

    private void autoSizeColumns(HSSFSheet sheet, List<Field> fields) {
        for (int i = 0; i < fields.size(); i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private List<Field> findAnnotatedFields() {
        return Arrays.stream(CarEntity.class.getDeclaredFields())
                        .filter(field -> field.isAnnotationPresent(ExcelHeader.class))
                        .collect(Collectors.toList());
    }

    private void createRows(List<CarEntity> cars, HSSFSheet sheet, List<Field> fields) {
        for (int i = 0; i < cars.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);
            for (int j = 0; j < fields.size(); j++) {
                Field field = fields.get(j);
                HSSFCell cell = row.createCell(j);
                ReflectionUtils.makeAccessible(field);
                Object fieldValue = ReflectionUtils.getField(field, cars.get(i));
                Optional.ofNullable(fieldValue)
                        .map(Object::toString)
                        .ifPresent(cell::setCellValue);
            }
        }
    }

    private void createHeader(HSSFSheet sheet, List<Field> fields) {
        HSSFRow header = sheet.createRow(0);
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            String value = field.getAnnotation(ExcelHeader.class).value();
            header.createCell(i).setCellValue(value);
        }
    }
}
