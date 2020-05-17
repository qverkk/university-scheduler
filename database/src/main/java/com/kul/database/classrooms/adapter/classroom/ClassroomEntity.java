package com.kul.database.classrooms.adapter.classroom;

import com.kul.database.classrooms.adapter.classroomtype.ClassroomTypeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "classrooms")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ClassroomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @ManyToOne
    private ClassroomTypeEntity classroomTypeEntity;

    public static ClassroomEntity newForName(String name) {
        return new ClassroomEntity(
                null,
                name,
                null
        );
    }
}
