package com.kul.window.application.data;

import com.kul.api.adapter.admin.management.lecturer.preferences.LecutrerPreferenecesUpdateException;
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
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class AdminViewModel {

    private final List<UserInfoViewModel> users = new LinkedList<>();
    private final ObservableList<UserInfoViewModel> observableUsers = FXCollections.observableList(users);
    private final AtomicBoolean fetchingLocked = new AtomicBoolean(false);
    private final ExecutorsFactory preconfiguredExecutors;
    private final UserManagement userManagement;
    private final UserInfoViewModel currentUserInfo;
    private final StringProperty responseMessage = new SimpleStringProperty();
    private final UpdatePreferenceViewModel updatePreferenceViewModel;
    private final AdminViewMessageResolver messageResolver = new DefaultAdminViewMessageResolver();

    public AdminViewModel(ExecutorsFactory preconfiguredExecutors, UserManagement userManagement, UserInfoViewModel currentUserInfo, UpdatePreferenceViewModel updatePreferenceViewModel) {
        this.preconfiguredExecutors = preconfiguredExecutors;
        this.userManagement = userManagement;
        this.currentUserInfo = currentUserInfo;
        this.updatePreferenceViewModel = updatePreferenceViewModel;
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

    public void fetchPreferences(DayOfWeek selectedItem, Long userId) {
        final ThreadPoolExecutor executor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor(
                "fetch-lecturer-preferences"
        );

        Single.fromCallable(() -> userManagement.fetchPreferences(userId, selectedItem))
                .subscribeOn(Schedulers.from(executor))
                .observeOn(preconfiguredExecutors.platformScheduler())
                .doFinally(executor::shutdown)
                .subscribe(response -> {
                    startTimeProperty().setValue(response.getStartTime());
                    endTimeProperty().setValue(response.getEndTime());
                }, error -> {
                    if (!(error instanceof LecutrerPreferenecesUpdateException)) {
                        return;
                    }
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
                            break;
                    }
                });
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
                        default:
                            responseMessage.setValue("Unknown exception");
                            break;
                    }
                });
    }
}
