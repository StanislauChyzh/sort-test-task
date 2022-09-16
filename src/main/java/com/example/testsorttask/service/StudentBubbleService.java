package com.example.testsorttask.service;

import com.example.testsorttask.dto.SortType;
import com.example.testsorttask.dto.StudentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.testsorttask.dto.SortType.BUBBLE;
import static java.util.Arrays.asList;
import static java.util.stream.IntStream.range;

@Service
public class StudentBubbleService extends StudentService {

    @Override
    public SortType getType() {
        return BUBBLE;
    }

    @Override
    public List<StudentDTO> sort(byte[] file) {
        final var students = toStudents(file);
        return sort(students);
    }

    private List<StudentDTO> sort(List<StudentDTO> students) {
        final var studentDTOS = students.toArray(new StudentDTO[0]);
        range(0, students.size() - 1).flatMap(i -> range(1, students.size() - i))
                .forEach(j -> {
                    if (studentDTOS[j - 1].getScore() > studentDTOS[j].getScore()) {
                        final var temp = studentDTOS[j];
                        studentDTOS[j] = studentDTOS[j - 1];
                        studentDTOS[j - 1] = temp;
                    }
                });
        return asList(studentDTOS);
    }
}
