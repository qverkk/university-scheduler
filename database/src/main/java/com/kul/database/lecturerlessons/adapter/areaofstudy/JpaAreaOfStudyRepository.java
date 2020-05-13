package com.kul.database.lecturerlessons.adapter.areaofstudy;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaAreaOfStudyRepository extends JpaRepository<AreaOfStudyEntity, Long> {
    Optional<AreaOfStudyEntity> findByArea(String area);

    Optional<AreaOfStudyEntity> findByDepartment(String department);

    Optional<AreaOfStudyEntity> findByAreaAndDepartment(String area, String department);
}
