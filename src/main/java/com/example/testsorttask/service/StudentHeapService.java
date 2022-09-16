package com.example.testsorttask.service;

import com.example.testsorttask.dto.SortType;
import com.example.testsorttask.dto.StudentDTO;
import org.springframework.stereotype.Service;


import java.util.List;

import static com.example.testsorttask.dto.SortType.HEAP;
import static java.util.Arrays.asList;

@Service
public class StudentHeapService extends StudentService {

    @Override
    public SortType getType() {
        return HEAP;
    }

    @Override
    public List<StudentDTO> sort(byte[] file) {
        final var studentDTOS = toStudents(file);
        final var students = studentDTOS.toArray(new StudentDTO[0]);
        sort(students);
        return asList(students);
    }

    public void sort(StudentDTO[] students) {
        final var size = students.length;
        for (int i = size / 2 - 1; i >= 0; i--) {
            sort(students, size, i);
        }
        for (int i = size - 1; i >= 0; i--) {
            var temp = students[0];
            students[0] = students[i];
            students[i] = temp;
            sort(students, i, 0);
        }
    }

    void sort(StudentDTO[] students, int n, int i) {
        var largest = i;
        var left = 2 * i + 1;
        var right = 2 * i + 2;
        if (left < n && students[left].getScore() > students[largest].getScore()) {
            largest = left;
        }
        if (right < n && students[right].getScore() > students[largest].getScore()) {
            largest = right;
        }
        if (largest != i) {
            var swap = students[i];
            students[i] = students[largest];
            students[largest] = swap;
            sort(students, n, largest);
        }
    }
}
