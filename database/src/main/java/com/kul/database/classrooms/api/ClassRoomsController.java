package com.kul.database.classrooms.api;

import com.kul.database.classrooms.adapter.classroom.ClassroomEntityMapper;
import com.kul.database.classrooms.api.model.classroom.*;
import com.kul.database.classrooms.api.model.classroomtype.*;
import com.kul.database.classrooms.domain.classroom.Classroom;
import com.kul.database.classrooms.domain.classroom.ClassroomService;
import com.kul.database.classrooms.domain.classroomtype.ClassroomType;
import com.kul.database.classrooms.domain.classroomtype.ClassroomTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/classroom")
public class ClassRoomsController {

    private final ClassroomService classroomService;
    private final ClassroomTypeService classroomTypeService;

    public ClassRoomsController(ClassroomService classroomService, ClassroomTypeService classroomTypeService) {
        this.classroomService = classroomService;
        this.classroomTypeService = classroomTypeService;
    }

    @PutMapping(
            value = "/type/add"
    )
    public AddClassroomTypeResponse addClassRoomType(@RequestBody AddClassroomTypeRequest request) {
        ClassroomType type = classroomTypeService.save(AddClassroomTypeMapper.toDomain(request));
        return AddClassroomTypeMapper.fromDomain(type);
    }

    @DeleteMapping(
            value = "/delete/type/{id}"
    )
    public void deleteClassroomType(@PathVariable Long id) {
        classroomTypeService.delete(id);
    }

    @PutMapping(
            value = "/add"
    )
    public AddOrUpdateClassroomResponse addOrUpdateClassroom(@RequestBody AddOrUpdateClassroomRequest request) {
        Classroom classroom = classroomService.addOrUpdate(AddOrUpdateClassroomMapper.toDomain(request));
        return AddOrUpdateClassroomMapper.fromDomain(classroom);
    }

    @DeleteMapping(
            value = "/delete/classroom/{id}"
    )
    public void deleteClassroom(@PathVariable Long id) {
        classroomService.delete(id);
    }

    @GetMapping(
            value = "/types"
    )
    public List<FetchClassroomTypesResponse> fetchAllTypes() {
        List<ClassroomType> all = classroomTypeService.findAll();
        return all.stream()
                .map(FetchClassroomTypesMapper::fromDomain)
                .collect(Collectors.toList());
    }

    @GetMapping(
            value = "/classrooms"
    )
    public List<FetchClassroomResponse> fetchClassrooms() {
        List<Classroom> all = classroomService.findAll();
        return all.stream()
                .map(FetchClassroomMapper::fromDomain)
                .collect(Collectors.toList());
    }
}
