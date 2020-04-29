package com.kul.window.application.data;

import com.kul.api.adapter.admin.management.lecturer.preferences.InsufficientLecturerPreferencesPriviliges;
import com.kul.api.adapter.admin.management.lecturer.preferences.LecturerCannotBeFound;
import com.kul.api.adapter.admin.management.lecturer.preferences.LecturerPreferenceAlreadyExistsException;
import com.kul.api.adapter.admin.management.lecturer.preferences.LecturerPreferenceDoesntExistException;
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
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.DayOfWeek;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
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

    public AdminViewModel(ExecutorsFactory preconfiguredExecutors, UserManagement userManagement, UserInfoViewModel currentUserInfo) {
        this.preconfiguredExecutors = preconfiguredExecutors;
        this.userManagement = userManagement;
        this.currentUserInfo = currentUserInfo;
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

    public void displayPreferences(Long userId) {
        AtomicBoolean update = new AtomicBoolean(false);
        Dialog<LecturerPreferences> dialog = new Dialog<>();

        ButtonType addNew = new ButtonType("Add new", ButtonBar.ButtonData.OK_DONE);
        ButtonType updateExisting = new ButtonType("Update existing", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addNew, updateExisting, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addNew) {
                return addNewPreferencesDialog(userId);
            } else if (dialogButton == updateExisting) {
                update.set(true);
                return updatePreferencesDialog(userId);
            }
            return null;
        });

        Optional<LecturerPreferences> success = dialog.showAndWait();
        success.ifPresent(preferences -> {

            final ThreadPoolExecutor executor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor(
                    "update-lecturer-preferences"
            );

            Single.fromCallable(() -> {
                if (update.get()) {
                    return userManagement.updatePreferences(preferences);
                } else {
                    return userManagement.addPreferences(preferences);
                }
            })
                    .subscribeOn(Schedulers.from(executor))
                    .observeOn(preconfiguredExecutors.platformScheduler())
                    .doFinally(executor::shutdown)
                    .subscribe(response -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        if (response == null) {
                            alert.setAlertType(Alert.AlertType.ERROR);
                            alert.setContentText("Failed to add/update preferences");
                        } else {
                            alert.setContentText("Success!");
                        }
                        alert.showAndWait();
                    }, error -> {
                        if (error instanceof InsufficientLecturerPreferencesPriviliges) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Not enough priviliges to update preferences for this user");
                            alert.showAndWait();
                        } else if (error instanceof LecturerCannotBeFound) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("This lecturer doesn't exist in our database");
                            alert.showAndWait();
                        } else if (error instanceof LecturerPreferenceDoesntExistException) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Preference for this day doesn't exist. Please add one.");
                            alert.showAndWait();
                        } else if (error instanceof LecturerPreferenceAlreadyExistsException) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Preference for this day already exists. Please update it.");
                            alert.showAndWait();
                        }
                    });
        });
    }

    private LecturerPreferences updatePreferencesDialog(Long userId) {
        Dialog<LecturerPreferences> dialog = new Dialog<>();

        ButtonType updateButton = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButton, ButtonType.CANCEL);

        final StringProperty startTimeProperty = new SimpleStringProperty("00:00");
        final StringProperty endTimeProperty = new SimpleStringProperty("00:00");

        final TextField startTime = new TextField();
        final TextField endTime = new TextField();
        final ComboBox<DayOfWeek> day = new ComboBox<>();

        startTime.textProperty().bind(startTimeProperty);
        endTime.textProperty().bind(endTimeProperty);
        day.setOnAction(event -> fetchPreferences(
                dialog,
                day.getSelectionModel().getSelectedItem(),
                userId,
                startTimeProperty,
                endTimeProperty
        ));

        dialog.getDialogPane().setContent(generateGridSettings(startTime, endTime, day));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButton) {
                return new LecturerPreferences(
                        userId,
                        startTime.getText(),
                        endTime.getText(),
                        day.getSelectionModel().getSelectedItem()
                );
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }

    private void fetchPreferences(Dialog<LecturerPreferences> dialog, DayOfWeek selectedItem,
                                  Long userId,
                                  StringProperty startTimeProperty,
                                  StringProperty endTimeProperty
    ) {

        final ThreadPoolExecutor executor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor(
                "fetch-lecturer-preferences"
        );

        Single.fromCallable(() -> userManagement.fetchPreferences(userId, selectedItem))
                .subscribeOn(Schedulers.from(executor))
                .observeOn(preconfiguredExecutors.platformScheduler())
                .doFinally(executor::shutdown)
                .subscribe(response -> {
                    startTimeProperty.setValue(response.getStartTime());
                    endTimeProperty.setValue(response.getEndTime());
                }, error -> {
                    if (error instanceof LecturerCannotBeFound) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("This lecturer doesn't exist in our database");
                        alert.showAndWait();
                    } else if (error instanceof LecturerPreferenceDoesntExistException) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Preference for this day doesn't exist. Please add one.");
                        alert.showAndWait();
                    }
                    dialog.close();
                });
    }

    private LecturerPreferences addNewPreferencesDialog(Long userId) {
        Dialog<LecturerPreferences> dialog = new Dialog<>();

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        final TextField startTime = new TextField();
        final TextField endTime = new TextField();
        final ComboBox<DayOfWeek> day = new ComboBox<>();

        dialog.getDialogPane().setContent(generateGridSettings(startTime, endTime, day));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                return new LecturerPreferences(
                        userId,
                        startTime.getText(),
                        endTime.getText(),
                        day.getSelectionModel().getSelectedItem()
                );
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }

    private GridPane generateGridSettings(TextField startTime, TextField endTime, ComboBox<DayOfWeek> day) {
        final GridPane grid = new GridPane();
        day.getItems().addAll(DayOfWeek.values());
        day.getSelectionModel().selectFirst();

        grid.add(new Label("Start time"), 0, 0);
        grid.add(new Label("End time"), 0, 1);
        grid.add(new Label("Day"), 0, 2);

        grid.add(startTime, 1, 0);
        grid.add(endTime, 1, 1);
        grid.add(day, 1, 2);
        return grid;
    }
}
