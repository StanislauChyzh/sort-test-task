package com.example.testsorttask.service;

import com.example.testsorttask.dto.SortType;
import com.example.testsorttask.dto.StudentDTO;
import com.example.testsorttask.exception.BusinessException;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.List;

import static com.opencsv.ICSVWriter.DEFAULT_SEPARATOR;
import static java.lang.System.currentTimeMillis;

@Slf4j
@Validated
public abstract class StudentService {

    public abstract List<StudentDTO> sort(byte[] students);

    public abstract SortType getType();

    public void toCsv(@NotNull List<StudentDTO> students) {
        try (final var writer  = new FileWriter(String.format("scores/students-%s.csv", currentTimeMillis()))) {
            final var sbc = new StatefulBeanToCsvBuilder<StudentDTO>(writer)
                    .withQuotechar('\'')
                    .withSeparator(DEFAULT_SEPARATOR)
                    .build();
            sbc.write(students);
        } catch (Exception e) {
            log.error("Can't write csv file: {}", e.getMessage());
            throw new BusinessException("Can't write csv file.");
        }
    }

    protected List<StudentDTO> toStudents(byte[] students) {
        final var csvReader = new CSVReader(new InputStreamReader(new ByteArrayInputStream(students)));
        return new CsvToBeanBuilder<StudentDTO>(csvReader)
                .withType(StudentDTO.class)
                .build()
                .parse();
    }
}
