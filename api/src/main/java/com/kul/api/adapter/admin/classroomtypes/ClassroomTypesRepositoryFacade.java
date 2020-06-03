package com.kul.api.adapter.admin.classroomtypes;

import com.kul.api.adapter.admin.external.ClassroomsEndpointClient;
import com.kul.api.adapter.admin.external.ErrorResponseException;
import com.kul.api.adapter.admin.management.lecturer.preferences.FailureCause;
import com.kul.api.adapter.admin.management.lecturer.preferences.LecutrerPreferenecesUpdateException;
import com.kul.api.domain.admin.classroomtypes.ClassroomTypes;
import com.kul.api.domain.admin.classroomtypes.ClassroomTypesRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ClassroomTypesRepositoryFacade implements ClassroomTypesRepository {

    private final ClassroomsEndpointClient client;

    public ClassroomTypesRepositoryFacade(ClassroomsEndpointClient client) {
        this.client = client;
    }

    @Override
    public List<ClassroomTypes> getAllClassroomTypes() {
        return client.getAllClassroomTypes().stream().map(type ->
            new ClassroomTypes(
                    type.getId(),
                    type.getName()
            )
        ).collect(Collectors.toList());
    }

    @Override
    public void removeClassroomTypeById(Long id) {
        client.deleteClassroomTypeById(id);
    }

    @Override
    public void addNewClassroomType(String classroomTypeName) {
        try {
            client.addNewClassroomType(
                    new AddClassroomType(classroomTypeName)
            );
        } catch (ErrorResponseException ex) {
            throw new AddNewClassroomTypeException(ex, FailureCause.findByCode(ex.getErrorResponse().getCode()));
        }
    }
}
