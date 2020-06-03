package com.kul.window.application.admin;

import com.jfoenix.controls.JFXButton;
import com.kul.api.domain.admin.classroomtypes.ClassroomTypes;
import com.kul.api.domain.admin.management.LecturerPreferences;
import com.kul.window.application.data.AdminViewModel;
import com.kul.window.application.data.ClassroomTypesInfoViewModel;
import com.kul.window.application.data.UserInfoViewModel;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminController implements Initializable {

    private static final Pattern TIME_PATTERN = Pattern.compile("^\\d{2}:\\d{2}$");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
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
    private TableView<ClassroomTypesInfoViewModel> classroomTypesTable;
    @FXML
    private TableColumn<ClassroomTypesInfoViewModel, String> typeNameCol;
    @FXML
    private TableColumn<ClassroomTypesInfoViewModel, String> typeActions;
    @FXML
    private JFXButton refreshUsersButton;
    @FXML
    private JFXButton addTypeButton;
    @FXML
    private JFXButton refreshTypesButton;

    public AdminController(AdminViewModel adminViewModel) {
        this.adminViewModel = adminViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usersTable.setItems(adminViewModel.users());
        classroomTypesTable.setItems(adminViewModel.classroomTypes());
        initializeUserColumns();
        initializeClassroomTypesColumns();
        adminViewModel.updateInfo();
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

    public void displayPreferences(Long userId) {
        Dialog<LecturerPreferences> dialog = new Dialog<>();

        ButtonType addNew = new ButtonType("Add new", ButtonBar.ButtonData.OK_DONE);
        ButtonType updateExisting = new ButtonType("Update existing", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addNew, updateExisting, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addNew) {
                return addNewPreferencesDialog(userId);
            } else if (dialogButton == updateExisting) {
                return updatePreferencesDialog(userId);
            }
            return null;
        });

        Optional<LecturerPreferences> success = dialog.showAndWait();
        success.ifPresent(adminViewModel::addOrUpdatePreference);
    }

    private LecturerPreferences updatePreferencesDialog(Long userId) {
        Dialog<LecturerPreferences> dialog = new Dialog<>();

        ButtonType updateButton = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButton, ButtonType.CANCEL);

        adminViewModel.startTimeProperty().setValue("00:00");
        adminViewModel.endTimeProperty().setValue("00:00");

        final TextField startTime = new TextField();
        final TextField endTime = new TextField();
        final ComboBox<DayOfWeek> day = new ComboBox<>();

        startTime.textProperty().bindBidirectional(adminViewModel.startTimeProperty());
        endTime.textProperty().bindBidirectional(adminViewModel.endTimeProperty());

        day.setOnAction(event -> adminViewModel.fetchPreferences(
                day.getSelectionModel().getSelectedItem(),
                userId
        ));

        dialog.getDialogPane().setContent(generateGridSettings(startTime, endTime, day));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButton) {
                String startTimeText = startTime.getText();
                String endTimeText = endTime.getText();
                if (!stringContainsTimePattern(startTimeText) || !stringContainsTimePattern(endTimeText)) {
                    adminViewModel.setInvalidFormatForTime();
                    return null;
                }
                return new LecturerPreferences(
                        userId,
                        stringToLocalTime(startTimeText),
                        stringToLocalTime(endTimeText),
                        day.getSelectionModel().getSelectedItem()
                );
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }

    private boolean stringContainsTimePattern(String str) {
        Matcher matcher = TIME_PATTERN.matcher(str);
        return matcher.matches();
    }

    private LocalTime stringToLocalTime(String time) {
        return LocalTime.parse(time, FORMATTER);
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
                String startTimeText = startTime.getText();
                String endTimeText = endTime.getText();
                if (!stringContainsTimePattern(startTimeText) || !stringContainsTimePattern(endTimeText)) {
                    adminViewModel.setInvalidFormatForTime();
                    return null;
                }
                return new LecturerPreferences(
                        userId,
                        stringToLocalTime(startTimeText),
                        stringToLocalTime(endTimeText),
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

        grid.addRow(0, new Label("Start time"), new Label("End time"), new Label("Day"));
        grid.addRow(1, startTime, endTime, day);

        return grid;
    }

    private void initializeClassroomTypesColumns() {
        typeNameCol.setCellValueFactory(param -> param.getValue().typeName());
        typeActions.setCellFactory(getTypeActionsCellFactory());
    }

    private Callback<TableColumn<ClassroomTypesInfoViewModel, String>, TableCell<ClassroomTypesInfoViewModel, String>> getTypeActionsCellFactory() {
        return new Callback<TableColumn<ClassroomTypesInfoViewModel, String>, TableCell<ClassroomTypesInfoViewModel, String>>() {
            @Override
            public TableCell<ClassroomTypesInfoViewModel, String> call(TableColumn<ClassroomTypesInfoViewModel, String> classroomTypesInfoViewModelStringTableColumn) {
                final TableCell<ClassroomTypesInfoViewModel, String> cell = new TableCell<ClassroomTypesInfoViewModel, String>() {

                    final Button removeBtn = new Button("Remove");

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            removeBtn.setMinWidth(150);
                            ClassroomTypesInfoViewModel classroomType = getTableView().getItems().get(getIndex());

                            removeBtn.setOnAction(e -> {
                                Long id = classroomType.id().get();
                                adminViewModel.removeClassroomTypeById(id);
                            });
                            setGraphic(removeBtn);
                        }
                    }
                };
                return cell;
            }
        };
    }

    private void initializeUserColumns() {
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
                            preferencesBtn.setOnAction(event -> displayPreferences(userId));

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

    @FXML
    void refreshClassTypes() {
        adminViewModel.refreshClassTypes();
    }

    @FXML
    void addNewClassType() {
        Dialog<ClassroomTypes> dialog = new Dialog<>();

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        final TextField classTypeName = new TextField();
        final GridPane grid = new GridPane();
        grid.addRow(0, new Label("Classroom type: "), classTypeName);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                String startTimeText = classTypeName.getText();
                return new ClassroomTypes(
                        null,
                        startTimeText
                );
            }
            return null;
        });

        ClassroomTypes result = dialog.showAndWait().orElse(null);
        if (result == null) {
            return;
        }
        adminViewModel.addNewClassroomType(result);
    }
}
