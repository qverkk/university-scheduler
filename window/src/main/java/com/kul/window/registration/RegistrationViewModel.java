package com.kul.window.registration;

import com.kul.api.adapter.user.registration.UserAccountAlreadyExistException;
import com.kul.api.adapter.user.registration.UserAccountCreationException;
import com.kul.api.domain.user.registration.NewUser;
import com.kul.api.domain.user.registration.UserRegistration;
import com.kul.api.model.AuthorityEnum;
import com.kul.window.ViewModel;
import com.kul.window.async.ExecutorsFactory;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.concurrent.ThreadPoolExecutor;

public class RegistrationViewModel {
    private final ViewModel mainViewModel;
    private final UserRegistration userRegistration;
    private final ExecutorsFactory preconfiguredExecutors;
    private final StringProperty registrationStatus = new SimpleStringProperty();
    private final BooleanProperty usernameError = new SimpleBooleanProperty(false);


    public RegistrationViewModel(ViewModel mainViewModel, UserRegistration userRegistration, ExecutorsFactory preconfiguredExecutors) {
        this.mainViewModel = mainViewModel;
        this.userRegistration = userRegistration;
        this.preconfiguredExecutors = preconfiguredExecutors;
    }

    public StringProperty registrationStatusProperty() {
        return registrationStatus;
    }

    public BooleanProperty usernameErrorProperty() {
        return usernameError;
    }

    public void resetFields() {
        registrationStatusProperty().setValue("");
        usernameErrorProperty().setValue(false);
    }

    public void register(String username, String password, String firstName, String lastName, AuthorityEnum authority) {
        resetFields();
        NewUser user = new NewUser(
                username,
                password,
                firstName,
                lastName,
                authority
        );

        final ThreadPoolExecutor executor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor("register-user");

        Single
                .fromCallable(() -> userRegistration.register(user))
                .subscribeOn(Schedulers.from(executor))
                .observeOn(preconfiguredExecutors.platformScheduler())
                .doFinally(executor::shutdown)
                .subscribe(registrationInfo -> {
                    registrationStatusProperty().setValue("Success registering account");
                }, error -> {
                    if (error instanceof UserAccountAlreadyExistException) {
                        usernameErrorProperty().setValue(true);
                    } else if (error instanceof UserAccountCreationException) {
                        registrationStatusProperty().setValue("Failure registrating account");
                    }
                });

    }

    public void openLoginMenu() {
        mainViewModel.openLoginMenu();
    }
}
