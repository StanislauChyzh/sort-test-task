package com.example.testsorttask.controller;

import com.example.testsorttask.dto.SortType;
import com.example.testsorttask.dto.StudentScoreResponse;
import com.example.testsorttask.exception.BusinessException;
import com.example.testsorttask.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.lang.System.currentTimeMillis;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final Map<SortType, StudentService> studentServices;

    public StudentController(List<StudentService> studentServices) {
        this.studentServices = studentServices.stream().collect(toMap(StudentService::getType, identity()));
    }

    @PostMapping("/sort")
    public ResponseEntity<StudentScoreResponse> sort(@RequestParam(defaultValue = "BUBBLE") SortType type,
                                                     @RequestParam(defaultValue = "false") boolean isWrite,
                                                     @NotNull MultipartFile file) {
        final var startProcessing = currentTimeMillis();
        final var service = studentServices.get(type);
        final var students = service.sort(getFileBytes(file));
        if (isWrite) {
            service.toCsv(students);
        }
        final var processingTime = getProcessingTime(startProcessing);
        final var response = new StudentScoreResponse(processingTime, students.size(), students);
        return ResponseEntity.ok(response);
    }

    private Long getProcessingTime(Long startProcessing) {
        return currentTimeMillis() - startProcessing;
    }

    private byte[] getFileBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new BusinessException("Can't pars file.");
        }
    }
}
