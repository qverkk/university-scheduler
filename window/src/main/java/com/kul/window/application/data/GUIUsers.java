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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class GUIUsers implements Users {

    private final List<UserInfoViewModel> users = new LinkedList<>();
    private final AdminController adminController;
    private final ObservableList<UserInfoViewModel> observableUsers = FXCollections.observableList(users);
    private final AtomicBoolean fetchingLocked = new AtomicBoolean(false);

    public GUIUsers(AdminController adminController) {
        this.adminController = adminController;
    }

    @Override
    public ObservableList<UserInfoViewModel> users() {
        return observableUsers;
    }

    @Override
    public void enableUser(Long id) {
        if (fetchingLocked.getAndSet(true)) {
            return;
        }
        Scheduler enableUserScheduler = Schedulers.from(
                PreconfiguredExecutors.noQueueNamedSingleThreadExecutor("enable-user-%d")
        );
        Completable.fromRunnable(() -> adminController.getUserManagement().enableUser(id))
                .subscribeOn(enableUserScheduler)
                .andThen(Single.fromCallable(this::getAllUsers))
                .observeOn(JavaFxScheduler.platform())
                .doFinally(enableUserScheduler::shutdown)
                .subscribe(usersList -> {
                    observableUsers.clear();
                    observableUsers.addAll(usersList);
                    fetchingLocked.set(false);
                });
    }

    @Override
    public void disableUser(Long id) {
        if (fetchingLocked.getAndSet(true)) {
            return;
        }
        Scheduler disableUserScheduler = Schedulers.from(
                PreconfiguredExecutors.noQueueNamedSingleThreadExecutor("disable-user-%d")
        );
        Completable.fromRunnable(() -> adminController.getUserManagement().disableUser(id))
                .subscribeOn(disableUserScheduler)
                .andThen(Single.fromCallable(this::getAllUsers))
                .observeOn(JavaFxScheduler.platform())
                .doFinally(disableUserScheduler::shutdown)
                .subscribe(usersList -> {
                    observableUsers.clear();
                    observableUsers.addAll(usersList);
                    fetchingLocked.set(false);
                });
    }

    @Override
    public void refresh() {
        if (fetchingLocked.getAndSet(true)) {
            return;
        }
        Scheduler fetchUserSchedler = Schedulers.from(
                PreconfiguredExecutors.noQueueNamedSingleThreadExecutor("fetch-users-%d")
        );
        Completable.complete()
                .subscribeOn(fetchUserSchedler)
                .andThen(Single.fromCallable(this::getAllUsers))
                .observeOn(JavaFxScheduler.platform())
                .doFinally(fetchUserSchedler::shutdown)
                .subscribe(usersList -> {
                    observableUsers.clear();
                    observableUsers.addAll(usersList);
                    fetchingLocked.set(false);
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
