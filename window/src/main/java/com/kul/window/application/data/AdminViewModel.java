package com.kul.window.application.data;

import com.kul.api.adapter.admin.classroomtypes.AddNewClassroomTypeException;
import com.kul.api.adapter.admin.classroomtypes.RemoveClassroomTypeException;
import com.kul.api.adapter.admin.management.lecturer.preferences.LecutrerPreferenecesUpdateException;
import com.kul.api.domain.admin.areaofstudies.AreaOfStudies;
import com.kul.api.domain.admin.areaofstudies.AreaOfStudiesManagement;
import com.kul.api.domain.admin.classroomtypes.ClassroomTypes;
import com.kul.api.domain.admin.classroomtypes.ClassroomTypesManagement;
import com.kul.api.domain.admin.classroomtypes.Classrooms;
import com.kul.api.domain.admin.management.LecturerPreferences;
import com.kul.api.domain.admin.management.ManagedUser;
import com.kul.api.domain.admin.management.UserManagement;
import com.kul.window.async.ExecutorsFactory;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class AdminViewModel {

    private final List<UserInfoViewModel> users = new LinkedList<>();
    private final List<ClassroomTypesInfoViewModel> classroomTypes = new LinkedList<>();
    private final List<ClassroomsInfoViewModel> classrooms = new LinkedList<>();
    private final List<AreaOfStudiesInfoViewModel> areaOfStudies = new LinkedList<>();
    private final ObservableList<UserInfoViewModel> observableUsers = FXCollections.observableList(users);
    private final ObservableList<ClassroomsInfoViewModel> observableClassrooms = FXCollections.observableList(classrooms);
    private final ObservableList<ClassroomTypesInfoViewModel> observableClassroomTypes = FXCollections.observableList(classroomTypes);
    private final ObservableList<AreaOfStudiesInfoViewModel> observableAreaOfStudies = FXCollections.observableList(areaOfStudies);
    private final AtomicBoolean fetchingLocked = new AtomicBoolean(false);
    private final ExecutorsFactory preconfiguredExecutors;
    private final UserManagement userManagement;
    private final ClassroomTypesManagement classroomTypesManagement;
    private final UserInfoViewModel currentUserInfo;
    private final StringProperty responseMessage = new SimpleStringProperty();
    private final UpdatePreferenceViewModel updatePreferenceViewModel;
    private final AdminViewMessageResolver messageResolver = new DefaultAdminViewMessageResolver();
    private final AreaOfStudiesManagement areaOfStudiesManagement;

    public AdminViewModel(ExecutorsFactory preconfiguredExecutors, UserManagement userManagement, ClassroomTypesManagement classroomTypesManagement, UserInfoViewModel currentUserInfo, UpdatePreferenceViewModel updatePreferenceViewModel, AreaOfStudiesManagement areaOfStudiesManagement) {
        this.preconfiguredExecutors = preconfiguredExecutors;
        this.userManagement = userManagement;
        this.classroomTypesManagement = classroomTypesManagement;
        this.currentUserInfo = currentUserInfo;
        this.updatePreferenceViewModel = updatePreferenceViewModel;
        this.areaOfStudiesManagement = areaOfStudiesManagement;
    }

    public StringProperty startTimeProperty() {
        return updatePreferenceViewModel.currentlyEditedPreferenceStartTimeProperty();
    }

    public StringProperty endTimeProperty() {
        return updatePreferenceViewModel.currentlyEditedPreferenceEndTimeProperty();
    }

    public StringProperty responseMessageProperty() {
        return responseMessage;
    }

    public void setInvalidFormatForTime() {
        responseMessage.setValue(messageResolver.nonValidPreferences());
    }

    public ObservableList<UserInfoViewModel> users() {
        return observableUsers;
    }

    public UserInfoViewModel getCurrentUserInfo() {
        return currentUserInfo;
    }

    public void enableUser(Long id) {
        if (fetchingLocked.getAndSet(true)) {
            return;
        }
        final ThreadPoolExecutor executor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor("enable-user");

        Completable.fromRunnable(() -> userManagement.enableUser(id))
                .subscribeOn(Schedulers.from(executor))
                .andThen(Single.fromCallable(this::getAllUsers))
                .observeOn(preconfiguredExecutors.platformScheduler())
                .doFinally(executor::shutdown)
                .subscribe(usersList -> {
                    observableUsers.clear();
                    observableUsers.addAll(usersList);
                    fetchingLocked.set(false);
                });
    }

    public void disableUser(Long id) {
        if (fetchingLocked.getAndSet(true)) {
            return;
        }
        final ThreadPoolExecutor executor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor("disable-user");

        Completable.fromRunnable(() -> userManagement.disableUser(id))
                .subscribeOn(Schedulers.from(executor))
                .andThen(Single.fromCallable(this::getAllUsers))
                .observeOn(preconfiguredExecutors.platformScheduler())
                .doFinally(executor::shutdown)
                .subscribe(usersList -> {
                    observableUsers.clear();
                    observableUsers.addAll(usersList);
                    fetchingLocked.set(false);
                });
    }

    public void refresh() {
        if (fetchingLocked.getAndSet(true)) {
            return;
        }
        final ThreadPoolExecutor executor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor("fetch-users");

        Single.fromCallable(this::getAllUsers)
                .subscribeOn(Schedulers.from(executor))
                .observeOn(preconfiguredExecutors.platformScheduler())
                .doFinally(executor::shutdown)
                .subscribe(usersList -> {
                    observableUsers.clear();
                    observableUsers.addAll(usersList);
                    fetchingLocked.set(false);
                });
    }

    private List<UserInfoViewModel> getAllUsers() {
        final List<ManagedUser> requestedUsers = userManagement.getAllUsers();
        return requestedUsers.stream().map(u ->
                new UserInfoViewModel(
                        u.getId(),
                        u.getFirstName(),
                        u.getLastName(),
                        u.getUsername(),
                        u.getEnabled(),
                        u.getAuthority()
                )
        ).collect(Collectors.toList());
    }

    private List<ClassroomTypesInfoViewModel> getAllClassroomTypes() {
        final List<ClassroomTypes> requestedUsers = classroomTypesManagement.getAllClassroomTypes();
        return requestedUsers.stream().map(u ->
                new ClassroomTypesInfoViewModel(
                        u.getId(),
                        u.getName()
                )
        ).collect(Collectors.toList());
    }

    private List<AreaOfStudiesInfoViewModel> getAllAreasOfStudies() {
        final List<AreaOfStudies> requestedAreaOfStudies = areaOfStudiesManagement.getAllAreaOfStudies();
        return requestedAreaOfStudies.stream().map(areas ->
                new AreaOfStudiesInfoViewModel(
                        areas.getId(),
                        areas.getAreaOfStudiesName(),
                        areas.getDepartmentName()
                )
        ).collect(Collectors.toList());
    }

    public void fetchPreferences(DayOfWeek selectedItem, Long userId) {
        final ThreadPoolExecutor executor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor(
                "fetch-lecturer-preferences"
        );

        Single.fromCallable(() -> userManagement.fetchPreferences(userId, selectedItem))
                .subscribeOn(Schedulers.from(executor))
                .observeOn(preconfiguredExecutors.platformScheduler())
                .doFinally(executor::shutdown)
                .subscribe(response -> {
                    startTimeProperty().setValue(localTimeToString(response.getStartTime()));
                    endTimeProperty().setValue(localTimeToString(response.getEndTime()));
                }, error -> {
                    LecutrerPreferenecesUpdateException ex = (LecutrerPreferenecesUpdateException) error;
                    switch (ex.getFailureCause()) {
                        case NoSuchUserProvided:
                            responseMessage.setValue(messageResolver.nonExistingLecturer());
                            break;
                        case LecturerPreferenceDoesntExist:
                            responseMessage.setValue(messageResolver.nonExistingPreference());
                            break;
                        default:
                            responseMessage.setValue("Unknown exception");
                            // TODO: Zalogowac blad
                            break;
                    }
                });
    }

    private String localTimeToString(LocalTime localTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.forLanguageTag("PL"));
        return formatter.format(localTime);
    }

    public void addOrUpdatePreference(LecturerPreferences preferences) {
        final ThreadPoolExecutor executor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor(
                "add-or-update-lecturer-preferences"
        );

        Single.fromCallable(() -> userManagement.updatePreferences(preferences))
                .subscribeOn(Schedulers.from(executor))
                .observeOn(preconfiguredExecutors.platformScheduler())
                .doFinally(executor::shutdown)
                .subscribe(response -> {
                    responseMessage.setValue(messageResolver.success());
                }, error -> {
                    if (!(error instanceof LecutrerPreferenecesUpdateException)) {
                        return;
                    }
                    LecutrerPreferenecesUpdateException ex = (LecutrerPreferenecesUpdateException) error;
                    switch (ex.getFailureCause()) {
                        case InsufficientPermissionsToUpdateLecturerPreferences:
                            responseMessage.setValue(messageResolver.insufficientLecturerPreferences());
                            break;
                        case NoSuchUserProvided:
                            responseMessage.setValue(messageResolver.nonExistingLecturer());
                            break;
                        case LecturerPreferenceDoesntExist:
                            responseMessage.setValue(messageResolver.nonExistingPreference());
                            break;
                        case LecturerPreferenceAlreadyExists:
                            responseMessage.setValue(messageResolver.alreadyExistingPreference());
                            break;
                        case MethodArgumentNotValidException:
                            responseMessage.setValue(messageResolver.nonValidPreferences());
                            break;
                        case LecturerPreferenceInvalidTime:
                            responseMessage.setValue(messageResolver.nonValidTime());
                            break;
                        default:
                            responseMessage.setValue("Unknown exception");
                            break;
                    }
                });
    }

    public void refreshClassTypes() {
        if (fetchingLocked.getAndSet(true)) {
            return;
        }
        final ThreadPoolExecutor executor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor("fetch-classroom-types");

        Single.fromCallable(this::getAllClassroomTypes)
                .subscribeOn(Schedulers.from(executor))
                .observeOn(preconfiguredExecutors.platformScheduler())
                .doFinally(executor::shutdown)
                .subscribe(typesList -> {
                    observableClassroomTypes.clear();
                    observableClassroomTypes.addAll(typesList);
                    fetchingLocked.set(false);
                });
    }

    public ObservableList<ClassroomTypesInfoViewModel> classroomTypes() {
        return observableClassroomTypes;
    }

    public ObservableList<ClassroomsInfoViewModel> classrooms() {
        return observableClassrooms;
    }

    public void removeClassroomTypeById(Long id) {
        if (fetchingLocked.getAndSet(true)) {
            return;
        }
        final ThreadPoolExecutor executor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor("remove-classroom-type");

        Completable.fromRunnable(() -> classroomTypesManagement.removeClassroomTypeById(id))
                .subscribeOn(Schedulers.from(executor))
                .andThen(Single.fromCallable(this::getAllClassroomTypes))
                .observeOn(preconfiguredExecutors.platformScheduler())
                .doFinally(executor::shutdown)
                .subscribe(typesList -> {
                    observableClassroomTypes.clear();
                    observableClassroomTypes.addAll(typesList);
                    fetchingLocked.set(false);
                }, error -> {
                    if (!(error instanceof RemoveClassroomTypeException)) {
                        return;
                    }
                    responseMessage.setValue(messageResolver.entityHasChildren());
                });
    }

    public void addNewClassroomType(ClassroomTypes result) {
        if (result.getName() == null) {
            return;
        }
        final ThreadPoolExecutor executor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor("add-classroom-type");

        Completable.fromRunnable(() -> classroomTypesManagement.addNewClassroomType(result.getName()))
                .subscribeOn(Schedulers.from(executor))
                .andThen(Single.fromCallable(this::getAllClassroomTypes))
                .observeOn(preconfiguredExecutors.platformScheduler())
                .doFinally(executor::shutdown)
                .subscribe(typesList -> {
                    observableClassroomTypes.clear();
                    observableClassroomTypes.addAll(typesList);
                    fetchingLocked.set(false);
                }, error -> {
                    if (!(error instanceof AddNewClassroomTypeException)) {
                        return;
                    }
                    responseMessage.setValue(messageResolver.classroomTypeAlreadyExists());
                });
    }

    public void addNewClassroom(Classrooms newClassroom) {
        if (fetchingLocked.getAndSet(true)) {
            return;
        }

        final ThreadPoolExecutor executor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor("add-classroom");

        Completable.fromRunnable(() -> classroomTypesManagement.addNewClassroom(newClassroom))
                .subscribeOn(Schedulers.from(executor))
                .andThen(Single.fromCallable(this::getAllClassrooms))
                .observeOn(preconfiguredExecutors.platformScheduler())
                .doFinally(executor::shutdown)
                .subscribe(typesList -> {
                    observableClassrooms.clear();
                    observableClassrooms.addAll(typesList);
                    fetchingLocked.set(false);
                }, error -> {

                });
    }

    public void refreshClassrooms() {
        if (fetchingLocked.getAndSet(true)) {
            return;
        }
        final ThreadPoolExecutor executor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor("fetch-classrooms");

        Single.fromCallable(this::getAllClassrooms)
                .subscribeOn(Schedulers.from(executor))
                .observeOn(preconfiguredExecutors.platformScheduler())
                .doFinally(executor::shutdown)
                .subscribe(classrooms -> {
                    observableClassrooms.clear();
                    observableClassrooms.addAll(classrooms);
                    fetchingLocked.set(false);
                });
    }

    private List<ClassroomsInfoViewModel> getAllClassrooms() {
        final List<Classrooms> requestedUsers = classroomTypesManagement.getAllClassrooms();
        return requestedUsers.stream().map(u ->
                new ClassroomsInfoViewModel(
                        u.getId(),
                        u.getName(),
                        u.getClassroomTypes(),
                        u.getClassroomSize()
                )
        ).collect(Collectors.toList());
    }

    public void updateInfo() {
        if (fetchingLocked.getAndSet(true)) {
            return;
        }
        final ThreadPoolExecutor userExecutor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor("fetch-info-user");
        final ThreadPoolExecutor classroomTypesExecutor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor("fetch-info-classroom-types");
        final ThreadPoolExecutor classroomsExecutor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor("fetch-info-classrooms");
        final ThreadPoolExecutor areaOfStudiesExecutor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor("fetch-info-area-of-studies");

        Single.fromCallable(this::getAllUsers)
                .subscribeOn(Schedulers.from(userExecutor))
                .observeOn(preconfiguredExecutors.platformScheduler())
                .doFinally(userExecutor::shutdown)
                .subscribe(usersList -> {
                    observableUsers.clear();
                    observableUsers.addAll(usersList);
                    fetchingLocked.set(false);
                });

        Single.fromCallable(this::getAllClassroomTypes)
                .subscribeOn(Schedulers.from(classroomTypesExecutor))
                .observeOn(preconfiguredExecutors.platformScheduler())
                .doFinally(classroomTypesExecutor::shutdown)
                .subscribe(typesList -> {
                    observableClassroomTypes.clear();
                    observableClassroomTypes.addAll(typesList);
                    fetchingLocked.set(false);
                });

        Single.fromCallable(this::getAllClassrooms)
                .subscribeOn(Schedulers.from(classroomsExecutor))
                .observeOn(preconfiguredExecutors.platformScheduler())
                .doFinally(classroomsExecutor::shutdown)
                .subscribe(classrooms -> {
                    observableClassrooms.clear();
                    observableClassrooms.addAll(classrooms);
                    fetchingLocked.set(false);
                });
    }

    public void deleteClassroom(long id) {
        if (fetchingLocked.getAndSet(true)) {
            return;
        }
        final ThreadPoolExecutor executor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor("remove-classroom");

        Completable.fromRunnable(() -> classroomTypesManagement.removeClassroomById(id))
                .subscribeOn(Schedulers.from(executor))
                .andThen(Single.fromCallable(this::getAllClassrooms))
                .observeOn(preconfiguredExecutors.platformScheduler())
                .doFinally(executor::shutdown)
                .subscribe(classrooms -> {
                    observableClassrooms.clear();
                    observableClassrooms.addAll(classrooms);
                    fetchingLocked.set(false);
                }, error -> {

                });
    }

    public void addNewAreaOfStudies(AreaOfStudies newAreaOfStudies) {
        if (fetchingLocked.getAndSet(true)) {
            return;
        }

        final ThreadPoolExecutor executor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor("add-area-of-studies");

        Completable.fromRunnable(() -> areaOfStudiesManagement.addNewAreaOfStudies(newAreaOfStudies))
                .subscribeOn(Schedulers.from(executor))
                .andThen(Single.fromCallable(this::getAllAreasOfStudies))
                .observeOn(preconfiguredExecutors.platformScheduler())
                .doFinally(executor::shutdown)
                .subscribe(areasList -> {
                    observableAreaOfStudies.clear();
                    observableAreaOfStudies.addAll(areasList);
                    fetchingLocked.set(false);
                }, error -> {

                });
    }

    public void refreshAreaOfStudies() {
        // TODO
        if (fetchingLocked.getAndSet(true)) {
            return;
        }

        final ThreadPoolExecutor executor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor("fetch-area-of-studies");

        Single.fromCallable(this::getAllAreasOfStudies)
                .subscribeOn(Schedulers.from(executor))
                .observeOn(preconfiguredExecutors.platformScheduler())
                .doFinally(executor::shutdown)
                .subscribe(areaOfStudies -> {
                    observableAreaOfStudies.clear();
                    observableAreaOfStudies.addAll(areaOfStudies);
                    fetchingLocked.set(false);
                });
    }

    public ObservableList<AreaOfStudiesInfoViewModel> areaOfStudies() {
        return observableAreaOfStudies;
    }

    public void deleteAreaOfStudy(String area, String department) {
        if (fetchingLocked.getAndSet(true)) {
            return;
        }
        final ThreadPoolExecutor executor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor("remove-area-of-studies");

        Completable.fromRunnable(() -> areaOfStudiesManagement.removeAreaOfStudies(area, department))
                .subscribeOn(Schedulers.from(executor))
                .andThen(Single.fromCallable(this::getAllAreasOfStudies))
                .observeOn(preconfiguredExecutors.platformScheduler())
                .doFinally(executor::shutdown)
                .subscribe(areas -> {
                    observableAreaOfStudies.clear();
                    observableAreaOfStudies.addAll(areas);
                    fetchingLocked.set(false);
                }, error -> {
                    // TODO :
                    // {"message":"Area Informatyka department WMIAK","code":"NoSuchAreaOfStudy"}
                    // catch that
                });
    }
}
