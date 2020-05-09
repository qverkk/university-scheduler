package com.kul.database.lecturerlessons.adapter.lesson;

import com.kul.database.lecturerlessons.adapter.lessontype.LessonTypeEntity;
import com.kul.database.lecturerlessons.adapter.lessontype.LessonTypeEntityMapper;
import com.kul.database.lecturerlessons.domain.AreaOfStudy;
import com.kul.database.lecturerlessons.domain.LecturerLessons;
import com.kul.database.usermanagement.domain.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "lecturer_lesson")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class LecturerLessonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @NotNull
    private String lessonName;

    @NotNull
    private AreaOfStudy areaOfStudy;

    @OneToOne
    private LessonTypeEntity lessonType;

    @NotNull
    private int semester;

    @NotNull
    private int year;

    @Version
    private Long version;

    public static LecturerLessonEntity fromDomain(LecturerLessons lecturerLessons) {
        return new LecturerLessonEntity(
                lecturerLessons.getId(),
                lecturerLessons.getUser(),
                lecturerLessons.getLessonName(),
                lecturerLessons.getAreaOfStudy(),
                LessonTypeEntityMapper.fromDomain(lecturerLessons.getLessonType()),
                lecturerLessons.getSemester(),
                lecturerLessons.getYear(),
                lecturerLessons.getVersion()
        );
    }

    public static LecturerLessons toDomain(LecturerLessonEntity entity) {
        return new LecturerLessons(
                entity.id,
                entity.user,
                entity.lessonName,
                entity.areaOfStudy,
                LessonTypeEntityMapper.toDomain(entity.getLessonType()),
                entity.semester,
                entity.year,
                entity.version
        );
    }
}
