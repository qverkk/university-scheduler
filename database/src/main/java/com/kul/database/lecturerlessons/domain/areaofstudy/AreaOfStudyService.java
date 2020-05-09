package com.kul.database.lecturerlessons.domain.areaofstudy;

import com.kul.database.lecturerlessons.api.model.areaofstudies.AddOrUpdateAreaOfStudiesRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaOfStudyService {

    private final AreaOfStudyRepository areaOfStudyRepository;

    public AreaOfStudyService(AreaOfStudyRepository areaOfStudyRepository) {
        this.areaOfStudyRepository = areaOfStudyRepository;
    }

    public List<AreaOfStudy> findAllAreaOfStudies() {
        return areaOfStudyRepository.findAll();
    }

    public AreaOfStudy addOrUpdateAreaOfStudy(AddOrUpdateAreaOfStudiesRequest request) {
        AreaOfStudy areaOfStudy = new AreaOfStudy(request.getDepartment(), request.getArea());
        return areaOfStudyRepository.addOrUpdateAreaOfStudy(areaOfStudy);
    }

    public void delete(String area, String department) {
        areaOfStudyRepository.delete(area, department);
    }
}
