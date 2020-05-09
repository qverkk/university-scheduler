package com.kul.database.lecturerlessons.adapter.areaofstudy;

import com.kul.database.lecturerlessons.domain.areaofstudy.AreaOfStudy;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity(name = "areas_of_study")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AreaOfStudyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String department;

    @NotNull
    private String area;
}
