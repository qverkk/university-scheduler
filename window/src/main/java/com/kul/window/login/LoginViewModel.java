package com.kul.window.login;

import com.kul.api.adapter.user.authorization.UserAccountDisabledException;
import com.kul.api.adapter.user.authorization.UserAuthorizationFacade;
import com.kul.api.adapter.user.authorization.UserLoginAccountDoesntExistException;
import com.kul.api.adapter.user.authorization.UserLoginWrongPasswordException;
import com.kul.api.domain.user.authorization.ExistingUser;
import com.kul.api.domain.user.authorization.ExistingUserToken;
import com.kul.api.domain.user.authorization.UserInfo;
import com.kul.api.model.AuthorityEnum;
import com.kul.window.ViewModel;
import com.kul.window.application.data.UserAndTokenInfo;
import com.kul.window.application.data.UserInfoViewModel;
import com.kul.window.application.helpers.ApplicationWindowManager;
import com.kul.window.async.ExecutorsFactory;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.stage.Stage;

import java.util.concurrent.ThreadPoolExecutor;

public class LoginViewModel {
    private final ViewModel mainViewModel;
    private final ApplicationWindowManager applicationWindowManager;
    private final UserAuthorizationFacade userAuthorizationFacade;
    private final ExecutorsFactory preconfiguredExecutors;

    private final BooleanProperty usernameError = new SimpleBooleanProperty(false);
    private final BooleanProperty passwordError = new SimpleBooleanProperty(false);
    private final BooleanProperty accountLocked = new SimpleBooleanProperty(false);
    private final BooleanProperty actionsLocked = new SimpleBooleanProperty(false);

    public LoginViewModel(
            ViewModel mainViewModel,
            ApplicationWindowManager applicationWindowManager,
            UserAuthorizationFacade userAuthorizationFacade,
            ExecutorsFactory preconfiguredExecutors
    ) {
        this.mainViewModel = mainViewModel;
        this.applicationWindowManager = applicationWindowManager;
        this.userAuthorizationFacade = userAuthorizationFacade;
        this.preconfiguredExecutors = preconfiguredExecutors;
    }

    public void setApplicationWindowStage(Stage stage) {
        this.applicationWindowManager.setCurrentStage(stage);
    }

    public BooleanProperty usernameErrorProperty() {
        return usernameError;
    }

    public BooleanProperty passwordErrorProperty() {
        return passwordError;
    }

    public BooleanProperty accountLockedProperty() {
        return accountLocked;
    }

    public BooleanProperty actionsLockedProperty() {
        return actionsLocked;
    }

    public void resetFields() {
        usernameErrorProperty().setValue(false);
        passwordErrorProperty().setValue(false);
        accountLockedProperty().setValue(false);
    }

    private ExistingUserToken authenticate(ExistingUser existingUser) throws UserAccountDisabledException, UserLoginAccountDoesntExistException, UserLoginWrongPasswordException {
        return userAuthorizationFacade.authenticate(existingUser);
    }

    private UserInfo loginWithToken(ExistingUserToken token) {
        return userAuthorizationFacade.loginWithToken(token);
    }

    public void signIn(String username, String password) {
        resetFields();
        final ExistingUser existingUser = new ExistingUser(
                username,
                password
        );
        actionsLocked.set(true);

        final ThreadPoolExecutor executor = preconfiguredExecutors.noQueueNamedSingleThreadExecutor("login-user");

        Single
                .fromCallable(() -> authenticate(existingUser))
                .subscribeOn(Schedulers.from(executor))
                .flatMap(token -> Single.fromCallable(() -> {
                    final UserInfo userInfo = loginWithToken(token);
                    return new UserAndTokenInfo(token, userInfo);
                }))
                .observeOn(preconfiguredExecutors.platformScheduler())
                .doFinally(executor::shutdown)
                .subscribe(userAndTokenInfo -> {
                    final UserInfo userInfo = userAndTokenInfo.getUserInfo();
                    final ExistingUserToken userToken = userAndTokenInfo.getUserToken();

                    final UserInfoViewModel guiUserInfo = new UserInfoViewModel(
                            0L,
                            userInfo.getFirstName(),
                            userInfo.getLastName(),
                            userInfo.getUsername(),
                            true,
                            userInfo.getAuthority()
                    );

                    if (userInfo.getAuthority() == AuthorityEnum.ADMIN) {
                        applicationWindowManager.openAdminPanel(guiUserInfo, userToken);
                    } else {
                        applicationWindowManager.openApplication(guiUserInfo);
                    }

                    actionsLocked.set(false);
                }, error -> {
                    if (error instanceof UserLoginAccountDoesntExistException) {
                        usernameErrorProperty().setValue(true);
                    } else if (error instanceof UserLoginWrongPasswordException) {
                        passwordErrorProperty().setValue(true);
                    } else if (error instanceof UserAccountDisabledException) {
                        accountLockedProperty().setValue(true);
                    }
                    actionsLocked.set(false);
                });
    }

    public void openRegistrationMenu() {
        mainViewModel.openRegistrationMenu();
    }
}
