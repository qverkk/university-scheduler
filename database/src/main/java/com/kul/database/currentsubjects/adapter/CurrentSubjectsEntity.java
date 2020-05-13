package com.kul.database.currentsubjects.adapter;

import com.kul.database.classrooms.adapter.classroom.ClassroomEntity;
import com.kul.database.lecturerlessons.adapter.lesson.LecturerLessonEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Entity(name = "current_subjects")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CurrentSubjectsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private ClassroomEntity classroomEntity;
    @ManyToOne
    private LecturerLessonEntity lecturerLessonEntity;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
}
