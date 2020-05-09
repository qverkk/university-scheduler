package com.kul.database.lecturerlessons.adapter.areaofstudy;

import com.kul.database.lecturerlessons.domain.areaofstudy.AreaOfStudy;
import com.kul.database.lecturerlessons.domain.areaofstudy.AreaOfStudyRepository;
import com.kul.database.lecturerlessons.domain.exceptions.NoSuchAreaOfStudy;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaAreaOfStudyRepositoryFacade implements AreaOfStudyRepository {

    private final JpaAreaOfStudyRepository areaOfStudyRepository;

    public JpaAreaOfStudyRepositoryFacade(JpaAreaOfStudyRepository areaOfStudyRepository) {
        this.areaOfStudyRepository = areaOfStudyRepository;
    }

    @Override
    public List<AreaOfStudy> findAll() {
        return areaOfStudyRepository.findAll().stream()
                .map(AreaOfStudyEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public AreaOfStudy addOrUpdateAreaOfStudy(AreaOfStudy areaOfStudy) {
        AreaOfStudyEntity areaOfStudyToUpdate = areaOfStudyRepository.findByArea(areaOfStudy.getArea())
                .orElse(AreaOfStudyEntityMapper.fromDomain(areaOfStudy));

        areaOfStudyToUpdate.setDepartment(areaOfStudy.getDepartment());

        AreaOfStudyEntity updatedAreaOfStudy = areaOfStudyRepository.save(areaOfStudyToUpdate);

        return AreaOfStudyEntityMapper.toDomain(updatedAreaOfStudy);
    }

    @Override
    public void delete(String area, String department) {
        AreaOfStudyEntity areaOfStudyEntity = areaOfStudyRepository.findByAreaAndDepartment(area, department)
                .orElseThrow(() -> new NoSuchAreaOfStudy("Area " + area + " department " + department));

        areaOfStudyRepository.delete(areaOfStudyEntity);
    }

    @Override
    public Optional<AreaOfStudy> findByAreaAndDepartment(String area, String department) {
        return areaOfStudyRepository.findByAreaAndDepartment(area, department)
                .map(AreaOfStudyEntityMapper::toDomain);
    }
}
