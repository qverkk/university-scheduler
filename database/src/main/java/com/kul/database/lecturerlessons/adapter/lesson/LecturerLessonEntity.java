package com.kul.database.lecturerlessons.adapter.lesson;

import com.kul.database.lecturerlessons.adapter.areaofstudy.AreaOfStudyEntity;
import com.kul.database.lecturerlessons.adapter.lessontype.LessonTypeEntity;
import com.kul.database.lecturerlessons.adapter.lessontype.LessonTypeEntityMapper;
import com.kul.database.lecturerlessons.domain.areaofstudy.AreaOfStudy;
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

    @ManyToOne
    private AreaOfStudyEntity areaOfStudy;

    @ManyToOne
    private LessonTypeEntity lessonType;

    @NotNull
    private int semester;

    @NotNull
    private int year;

    @Version
    private Long version;
}
