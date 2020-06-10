package com.kul.api.adapter.admin.areaofstudies;

import com.kul.api.adapter.admin.external.AreaOfStudiesEndpointClient;
import com.kul.api.adapter.admin.external.ErrorResponseException;
import com.kul.api.domain.admin.areaofstudies.AreaOfStudies;
import com.kul.api.domain.admin.areaofstudies.AreaOfStudiesRepository;

import java.util.List;
import java.util.stream.Collectors;

public class AreaOfStudiesRepositoryFacade implements AreaOfStudiesRepository {

    private final AreaOfStudiesEndpointClient client;

    public AreaOfStudiesRepositoryFacade(AreaOfStudiesEndpointClient client) {
        this.client = client;
    }

    @Override
    public void addNewAreaOfStudies(AreaOfStudies newAreaOfStudies) {
        try {
            client.addNewAreaOfStudies(
                    new AddAreaOfStudies(
                            newAreaOfStudies.getAreaOfStudiesName(),
                            newAreaOfStudies.getDepartmentName()
                    )
            );
        } catch (ErrorResponseException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<AreaOfStudies> getAllAreaOfStudies() {
        return client.getAreaOfStudies().getAreaOfStudies().stream().map(areas -> new AreaOfStudies(
                areas.getId(),
                areas.getArea(),
                areas.getDepartment()
        )).collect(Collectors.toList());
    }

    @Override
    public void removeAreaOfStudies(String area, String department) {
        client.deleteAreaOfStudies(area, department);
    }
}
