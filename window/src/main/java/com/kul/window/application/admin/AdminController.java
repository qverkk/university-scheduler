package com.kul.window.application.admin;

import com.jfoenix.controls.JFXButton;
import com.kul.api.domain.admin.management.UserManagement;
import com.kul.api.domain.user.authorization.UserInfo;
import com.kul.window.application.admin.data.UserProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AdminController implements Initializable {

    private final UserInfo userInfo;
    private final UserManagement userManagement;

    @FXML
    private TableView<UserProperty> usersTable;

    @FXML
    private TableColumn<UserProperty, String> idCol;
    @FXML
    private TableColumn<UserProperty, String> firstnameCol;
    @FXML
    private TableColumn<UserProperty, String> lastnameCol;
    @FXML
    private TableColumn<UserProperty, String> usernameCol;
    @FXML
    private TableColumn<UserProperty, Boolean> enabledCol;
    @FXML
    private TableColumn<UserProperty, String> authorityCol;
    @FXML
    private TableColumn<UserProperty, String> actionsCol;

    @FXML
    private JFXButton refreshUsersButton;

    public AdminController(UserInfo userInfo, UserManagement userManagement) {
        this.userInfo = userInfo;
        this.userManagement = userManagement;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumns();
        refreshUsers();
    }

    private void initializeColumns() {
        idCol.setCellValueFactory(param -> param.getValue().idProperty());
        firstnameCol.setCellValueFactory(param -> param.getValue().firstNameProperty());
        lastnameCol.setCellValueFactory(param -> param.getValue().lastNameProperty());
        usernameCol.setCellValueFactory(param -> param.getValue().usernameProperty());
        enabledCol.setCellValueFactory(param -> param.getValue().enabledProperty());
        authorityCol.setCellValueFactory(param -> param.getValue().authorityProperty());
        actionsCol.setCellFactory(getActionsCellFactory());
    }

    private Callback<TableColumn<UserProperty, String>, TableCell<UserProperty, String>> getActionsCellFactory() {
        return new Callback<TableColumn<UserProperty, String>, TableCell<UserProperty, String>>() {
            @Override
            public TableCell<UserProperty, String> call(final TableColumn<UserProperty, String> param) {
                final TableCell<UserProperty, String> cell = new TableCell<UserProperty, String>() {

                    final Button btn = new Button("Enable");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            btn.setOnAction(event -> {
                                UserProperty person = getTableView().getItems().get(getIndex());
                                // TODO: Make call to endpoint to enable user
                            });
                            setGraphic(btn);
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
        usersTable.getItems().clear();
        List<UserProperty> userPropertyList = userManagement.getAllUsers().stream().map(u ->
                new UserProperty(
                        u.getId(),
                        u.getFirstName(),
                        u.getLastName(),
                        u.getUsername(),
                        u.getEnabled(),
                        u.getAuthority()
                )
        ).collect(Collectors.toList());
        usersTable.getItems().addAll(userPropertyList);
    }
}
