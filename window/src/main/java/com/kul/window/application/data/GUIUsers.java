package com.kul.window.application.data;

import com.kul.api.domain.admin.management.ManagedUser;
import com.kul.window.application.admin.AdminController;
import com.kul.window.async.PreconfiguredExecutors;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class GUIUsers implements Users {

    private final List<UserInfoViewModel> users = new LinkedList<>();
    private final AdminController adminController;
    private final ObservableList<UserInfoViewModel> observableUsers = FXCollections.observableList(users);
    private volatile boolean fetchingLocked = false;

    public GUIUsers(AdminController adminController) {
        this.adminController = adminController;
    }

    @Override
    public ObservableList<UserInfoViewModel> users() {
        return observableUsers;
    }

    @Override
    public void enableUser(Long id) {
        if (fetchingLocked) {
            return;
        }
        fetchingLocked = true;
        Scheduler enableUserScheduler = Schedulers.from(
                PreconfiguredExecutors.noQueueNamedSingleThreadExecutor("enable-user-%d")
        );
        Completable.fromRunnable(() -> adminController.getUserManagement().enableUser(id))
                .subscribeOn(enableUserScheduler)
                .andThen(Single.fromCallable(this::getAllUsers))
                .observeOn(JavaFxScheduler.platform())
                .subscribe(usersList -> {
                    observableUsers.clear();
                    observableUsers.addAll(usersList);
                    fetchingLocked = false;
                });
    }

    @Override
    public void disableUser(Long id) {
        if (fetchingLocked) {
            return;
        }
        fetchingLocked = true;
//        new Thread(() -> {
//            adminController.getUserManagement().disableUser(id);
//            refresh();
//        }).start();
        Scheduler enableUserScheduler = Schedulers.from(
                PreconfiguredExecutors.noQueueNamedSingleThreadExecutor("disable-user-%d")
        );
        Completable.fromRunnable(() -> adminController.getUserManagement().disableUser(id))
                .subscribeOn(enableUserScheduler)
                .andThen(Single.fromCallable(this::getAllUsers))
                .observeOn(JavaFxScheduler.platform())
                .subscribe(usersList -> {
                    observableUsers.clear();
                    observableUsers.addAll(usersList);
                    fetchingLocked = false;
                });
    }

    @Override
    public void refresh() {
        if (fetchingLocked) {
            return;
        }
        fetchingLocked = true;
        Scheduler enableUserScheduler = Schedulers.from(
                PreconfiguredExecutors.noQueueNamedSingleThreadExecutor("fetch-users-%d")
        );
        Completable.complete()
                .subscribeOn(enableUserScheduler)
                .andThen(Single.fromCallable(this::getAllUsers))
                .observeOn(JavaFxScheduler.platform())
                .subscribe(usersList -> {
                    observableUsers.clear();
                    observableUsers.addAll(usersList);
                    fetchingLocked = false;
                });
    }

    private List<UserInfoViewModel> getAllUsers() {
        final List<ManagedUser> requestedUsers = adminController.getUserManagement().getAllUsers();
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
}
