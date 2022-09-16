package com.example.testsorttask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentScoreResponse {

    private Long processingTime;
    private Integer studentAmount;
    private List<StudentDTO> students;
}
