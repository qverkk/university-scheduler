package com.kul.window.application.admin;

import com.jfoenix.controls.JFXButton;
import com.kul.api.model.AuthorityEnum;
import com.kul.window.application.data.AdminViewModel;
import com.kul.window.application.data.UserInfoViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    private final AdminViewModel adminViewModel;

    @FXML
    private TableView<UserInfoViewModel> usersTable;

    @FXML
    private TableColumn<UserInfoViewModel, Number> idCol;
    @FXML
    private TableColumn<UserInfoViewModel, String> firstnameCol;
    @FXML
    private TableColumn<UserInfoViewModel, String> lastnameCol;
    @FXML
    private TableColumn<UserInfoViewModel, String> usernameCol;
    @FXML
    private TableColumn<UserInfoViewModel, Boolean> enabledCol;
    @FXML
    private TableColumn<UserInfoViewModel, String> authorityCol;
    @FXML
    private TableColumn<UserInfoViewModel, String> actionsCol;

    @FXML
    private JFXButton refreshUsersButton;

    public AdminController(AdminViewModel adminViewModel) {
        this.adminViewModel = adminViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usersTable.setItems(adminViewModel.users());
        initializeColumns();
        refreshUsers();
        initializeErrorAlertListener();
    }

    private void initializeErrorAlertListener() {
        adminViewModel.responseMessageProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                return;
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Label response = new Label(newValue);
            response.setWrapText(true);
            alert.getDialogPane().setContent(response);
            if (!newValue.equals("Success!")) {
                alert.setAlertType(Alert.AlertType.ERROR);
            }
            alert.showAndWait();
            adminViewModel.responseMessageProperty().setValue("");
        });
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

    private Callback<TableColumn<UserInfoViewModel, String>, TableCell<UserInfoViewModel, String>> getActionsCellFactory() {
        return new Callback<TableColumn<UserInfoViewModel, String>, TableCell<UserInfoViewModel, String>>() {
            @Override
            public TableCell<UserInfoViewModel, String> call(final TableColumn<UserInfoViewModel, String> param) {
                final TableCell<UserInfoViewModel, String> cell = new TableCell<UserInfoViewModel, String>() {

                    final Button enableBtn = new Button("Enable");
                    final Button disableBtn = new Button("Disable");
                    final Button preferencesBtn = new Button("Preferences");
                    final HBox pane = new HBox();
                    final VBox vpane = new VBox();

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            enableBtn.setMinWidth(75);
                            disableBtn.setMinWidth(75);
                            preferencesBtn.setMinWidth(150);

                            UserInfoViewModel person = getTableView().getItems().get(getIndex());
                            enableBtn.disableProperty()
                                    .bind(person.username().isEqualTo(adminViewModel.getCurrentUserInfo().username()).or(person.canBeEnabled()));

                            disableBtn.disableProperty()
                                    .bind(person.username().isEqualTo(adminViewModel.getCurrentUserInfo().username()).or(person.canBeDisabled()));

                            final Long userId = person.id().get();
                            enableBtn.setOnAction(event -> adminViewModel.enableUser(userId));
                            disableBtn.setOnAction(event -> adminViewModel.disableUser(userId));
                            preferencesBtn.setOnAction(event -> adminViewModel.displayPreferences(userId));

                            if (!pane.getChildren().containsAll(Arrays.asList(enableBtn, disableBtn))) {
                                pane.getChildren().addAll(enableBtn, disableBtn);
                                vpane.getChildren().add(pane);
                            }
                            if (person.canContainPreferences()) {
                                if (!pane.getChildren().contains(preferencesBtn)) {
                                    pane.getChildren().add(preferencesBtn);
                                    vpane.getChildren().add(preferencesBtn);
                                }
                            }
                            setGraphic(vpane);
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
        adminViewModel.refresh();
    }
}
