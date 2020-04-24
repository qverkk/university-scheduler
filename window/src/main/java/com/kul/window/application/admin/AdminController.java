package com.kul.window.application.admin;

import com.jfoenix.controls.JFXButton;
import com.kul.api.domain.admin.management.UserManagement;
import com.kul.api.domain.user.authorization.UserInfo;
import com.kul.window.application.data.GUIUsers;
import com.kul.window.application.data.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    private final UserInfo userInfo;
    private final UserManagement userManagement;

    private final GUIUsers users = new GUIUsers(this);

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, Number> idCol;
    @FXML
    private TableColumn<User, String> firstnameCol;
    @FXML
    private TableColumn<User, String> lastnameCol;
    @FXML
    private TableColumn<User, String> usernameCol;
    @FXML
    private TableColumn<User, Boolean> enabledCol;
    @FXML
    private TableColumn<User, String> authorityCol;
    @FXML
    private TableColumn<User, String> actionsCol;

    @FXML
    private JFXButton refreshUsersButton;

    public AdminController(UserInfo userInfo, UserManagement userManagement) {
        this.userInfo = userInfo;
        this.userManagement = userManagement;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usersTable.setItems(users.users());
        initializeColumns();
        refreshUsers();
    }

    private void initializeColumns() {
        idCol.setCellValueFactory(param -> param.getValue().id());
        idCol.setCellValueFactory(param -> param.getValue().id());
        firstnameCol.setCellValueFactory(param -> param.getValue().firstName());
        lastnameCol.setCellValueFactory(param -> param.getValue().lastName());
        usernameCol.setCellValueFactory(param -> param.getValue().username());
        enabledCol.setCellValueFactory(param -> param.getValue().enabled());
        authorityCol.setCellValueFactory(param -> param.getValue().authority());
        actionsCol.setCellFactory(getActionsCellFactory());
    }

    private Callback<TableColumn<User, String>, TableCell<User, String>> getActionsCellFactory() {
        return new Callback<TableColumn<User, String>, TableCell<User, String>>() {
            @Override
            public TableCell<User, String> call(final TableColumn<User, String> param) {
                final TableCell<User, String> cell = new TableCell<User, String>() {

                    final Button enableBtn = new Button("Enable");
                    final Button disableBtn = new Button("Disable");
                    final HBox pane = new HBox();

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            User person = getTableView().getItems().get(getIndex());
                            enableBtn.disableProperty()
                                    .bind(person.username().isEqualTo(userInfo.getUsername()).or(person.canBeEnabled()));

                            disableBtn.disableProperty()
                                    .bind(person.username().isEqualTo(userInfo.getUsername()).or(person.canBeDisabled()));

                            final Long userId = person.id().get();
                            enableBtn.setOnAction(event -> users.enableUser(userId));
                            disableBtn.setOnAction(event -> users.disableUser(userId));

                            if (!pane.getChildren().containsAll(Arrays.asList(enableBtn, disableBtn))) {
                                pane.getChildren().addAll(enableBtn, disableBtn);
                            }
                            setGraphic(pane);
                        }
                        setText(null);
                    }
                };
                return cell;
            }
        };
    }

    @FXML
    void refreshUsers() {
        users.refresh();
    }

    public UserManagement getUserManagement() {
        return userManagement;
    }
}
