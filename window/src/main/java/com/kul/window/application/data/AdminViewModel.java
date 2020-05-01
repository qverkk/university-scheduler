package com.kul.window.application.data;

import com.kul.api.adapter.admin.management.lecturer.preferences.*;
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
                    if (error instanceof LecturerCannotBeFound) {
                        responseMessage.setValue("This lecturer doesn't exist in our database");
                    } else if (error instanceof LecturerPreferenceDoesntExistException) {
                        responseMessage.setValue("Preference for this day doesn't exist. Please add one.");
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
                    responseMessage.setValue("Success!");
                }, error -> {
                    if (error instanceof InsufficientLecturerPreferencesPriviliges) {
                        responseMessage.setValue("Not enough priviliges to update preferences for this user");
                    } else if (error instanceof LecturerCannotBeFound) {
                        responseMessage.setValue("This lecturer doesn't exist in our database");
                    } else if (error instanceof LecturerPreferenceDoesntExistException) {
                        responseMessage.setValue("Preference for this day doesn't exist. Please add one.");
                    } else if (error instanceof LecturerPreferenceAlreadyExistsException) {
                        responseMessage.setValue("Preference for this day already exists. Please update it.");
                    } else if (error instanceof InvalidLecturerPreferencesException) {
                        responseMessage.setValue("Day must be selected and start/end time must match 00:00, 10:00 etc");
                    } else if (error instanceof BadUpdateLecturerPreferenceException) {
                        responseMessage.setValue("Day must be selected and start/end time must match 00:00, 10:00 etc");
                    }
                });
    }
}
