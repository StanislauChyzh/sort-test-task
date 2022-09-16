package com.example.testsorttask.service;

import com.example.testsorttask.dto.SortType;
import com.example.testsorttask.dto.StudentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.testsorttask.dto.SortType.MERGE;
import static java.lang.System.arraycopy;
import static java.util.Arrays.asList;

@Service
public class StudentMergeService extends StudentService {

    @Override
    public SortType getType() {
        return MERGE;
    }

    @Override
    public List<StudentDTO> sort(byte[] file) {
        final var students = toStudents(file);
        final var studentDTOS = students.toArray(new StudentDTO[0]);
        mergeSort(studentDTOS, studentDTOS.length);
        return asList(studentDTOS);
    }

    private void mergeSort(StudentDTO[] students, int size) {
        if (size < 2) {
            return;
        }
        final var mid = size / 2;
        final var left = new StudentDTO[mid];
        final var right = new StudentDTO[size - mid];
        arraycopy(students, 0, left, 0, mid);
        if (size - mid >= 0) {
            arraycopy(students, mid, right, 0, size - mid);
        }
        mergeSort(left, mid);
        mergeSort(right, size - mid);
        merge(students, left, right, mid, size - mid);
    }

    private void merge(StudentDTO[] students, StudentDTO[] l, StudentDTO[] r, int left, int right) {
        var i = 0;
        var j = 0;
        var k = 0;
        while (i < left && j < right) {
            if (l[i].getScore() <= r[j].getScore()) {
                students[k++] = l[i++];
                continue;
            }
            students[k++] = r[j++];
        }
        while (i < left) {
            students[k++] = l[i++];
        }
        while (j < right) {
            students[k++] = r[j++];
        }
    }
}
