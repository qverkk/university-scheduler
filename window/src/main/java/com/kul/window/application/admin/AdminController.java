package com.kul.window.application.admin;

import com.jfoenix.controls.JFXButton;
import com.kul.api.domain.admin.areaofstudies.AreaOfStudies;
import com.kul.api.domain.admin.classroomtypes.ClassroomTypes;
import com.kul.api.domain.admin.classroomtypes.Classrooms;
import com.kul.api.domain.admin.management.LecturerPreferences;
import com.kul.window.application.data.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AdminController implements Initializable {

    private static final Pattern TIME_PATTERN = Pattern.compile("^\\d{2}:\\d{2}$");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private final AdminViewModel adminViewModel;
    /**
     * Users
     */
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

    /**
     * Classroom types
     */
    @FXML
    private TableView<ClassroomTypesInfoViewModel> classroomTypesTable;
    @FXML
    private TableColumn<ClassroomTypesInfoViewModel, String> typeNameCol;
    @FXML
    private TableColumn<ClassroomTypesInfoViewModel, String> typeActions;
    @FXML
    private JFXButton addTypeButton;
    @FXML
    private JFXButton refreshTypesButton;

    /**
     * Classrooms
     */
    @FXML
    private TableView<ClassroomsInfoViewModel> classroomsTable;
    @FXML
    private TableColumn<ClassroomsInfoViewModel, String> classroomNameCol;
    @FXML
    private TableColumn<ClassroomsInfoViewModel, String> classroomSizeCol;
    @FXML
    private TableColumn<ClassroomsInfoViewModel, String> classroomTypesCol;
    @FXML
    private TableColumn<ClassroomsInfoViewModel, String> classroomActionsCol;
    @FXML
    private JFXButton addClassroomButton;
    @FXML
    private JFXButton refreshClassroomsButton;

    /**
     * Area of studies
     */
    @FXML
    private TableView<AreaOfStudiesInfoViewModel> areaOfStudiesTable;
    @FXML
    private TableColumn<AreaOfStudiesInfoViewModel, String> areaOfStudiesNameCol;
    @FXML
    private TableColumn<AreaOfStudiesInfoViewModel, String> departmentNameCol;
    @FXML
    private JFXButton addAreaOfStudiesButton;
    @FXML
    private JFXButton refreshAreaOfStudiesButton;


    public AdminController(AdminViewModel adminViewModel) {
        this.adminViewModel = adminViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usersTable.setItems(adminViewModel.users());
        classroomTypesTable.setItems(adminViewModel.classroomTypes());
        classroomsTable.setItems(adminViewModel.classrooms());
        areaOfStudiesTable.setItems(adminViewModel.areaOfStudies());

        initializeUserColumns();
        initializeClassroomTypesColumns();
        initializeClassroomsColumns();
        initializeClassroomsMenuActions();
        initializeAreaOfStudiesColumns();
        initializeAreaOfStudiesMenuActions();

        adminViewModel.updateInfo();
        initializeErrorAlertListener();
    }

    private void initializeAreaOfStudiesMenuActions() {
        final ContextMenu contextMenu = new ContextMenu();

        final MenuItem delete = new MenuItem("Delete");

        delete.setOnAction(actionEvent -> {
            final AreaOfStudiesInfoViewModel item = areaOfStudiesTable.getSelectionModel().getSelectedItem();
            if (item == null) {
                return;
            }
            adminViewModel.deleteAreaOfStudy(
                    item.areaOfStudiesName().get(),
                    item.departmentName().get()
            );
        });

        contextMenu.getItems().addAll(delete);

        areaOfStudiesTable.setContextMenu(contextMenu);
    }

    private void initializeClassroomsMenuActions() {
        final ContextMenu contextMenu = new ContextMenu();

        final MenuItem edit = new MenuItem("Edit");
        final MenuItem delete = new MenuItem("Delete");

        delete.setOnAction(actionEvent -> {
            final ClassroomsInfoViewModel item = classroomsTable.getSelectionModel().getSelectedItem();
            if (item == null) {
                return;
            }
            adminViewModel.deleteClassroom(item.id().get());
        });

        edit.setOnAction(actionEvent -> {
            final ClassroomsInfoViewModel item = classroomsTable.getSelectionModel().getSelectedItem();
            if (item == null) {
                return;
            }
            displayUpdateClassroomDialog(item.classroomName().get(), item.classroomSize().get(), item.types(), true);
        });

        contextMenu.getItems().addAll(edit, delete);

        classroomsTable.setContextMenu(contextMenu);
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

    private void initializeClassroomsColumns() {
        classroomNameCol.setCellValueFactory(param -> param.getValue().classroomName());
        classroomSizeCol.setCellValueFactory(param -> param.getValue().classroomSize());
        classroomTypesCol.setCellValueFactory(param -> param.getValue().classroomTypes());
    }

    private void initializeAreaOfStudiesColumns() {
        areaOfStudiesNameCol.setCellValueFactory(param -> param.getValue().areaOfStudiesName());
        departmentNameCol.setCellValueFactory(param -> param.getValue().departmentName());
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
    void addAreaOfStudies() {
        Dialog<AreaOfStudies> dialog = new Dialog<>();

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        final TextField areaOfStudies = new TextField();
        final TextField departmentName = new TextField();
        final GridPane grid = new GridPane();
        grid.addRow(0, new Label("Area of studies: "), areaOfStudies);
        grid.addRow(1, new Label("Department name: "), departmentName);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                String area = areaOfStudies.getText();
                String department = departmentName.getText();
                if (area.isEmpty() || department.isEmpty()) {
                    adminViewModel.responseMessageProperty().setValue("Area of study must be filled \n" +
                            "Department name must be filled");
                    return null;
                }
                return new AreaOfStudies(
                        null,
                        area,
                        department
                );
            }
            return null;
        });

        AreaOfStudies result = dialog.showAndWait().orElse(null);
        if (result == null) {
            return;
        }
        adminViewModel.addNewAreaOfStudies(result);
    }

    @FXML
    void refreshAreaOfStudies() {
        adminViewModel.refreshAreaOfStudies();
    }

    @FXML
    void addNewClassroom() {
        displayUpdateClassroomDialog("", "", Collections.emptyList(), false);
    }

    private void displayUpdateClassroomDialog(String name, String size, List<String> items, boolean update) {
        Dialog<Classrooms> dialog = new Dialog<>();

        ButtonType addButton = new ButtonType(update ? "Update" : "Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        final List<String> _addedTypes = new ArrayList<>();
        final ObservableList<String> addedTypes = FXCollections.observableList(_addedTypes);
        addedTypes.addAll(items);
        final TextField classroomName = new TextField(name);
        final TextField classroomSize = new TextField(size);
        final Button addTypeButton = new Button("Add");
        final ComboBox<String> fetchedTypes = new ComboBox<>(FXCollections.observableList(
                adminViewModel.classroomTypes()
                        .stream()
                        .map(item -> item.typeName().get())
                        .collect(Collectors.toList())
        ));
        final GridPane grid = new GridPane();
        final ListView<String> stringListView = new ListView<>(addedTypes);

        final ContextMenu menu = new ContextMenu();
        final MenuItem deleteButton = new MenuItem("Delete");
        deleteButton.setOnAction(actionEvent -> {
            final String selectedItem = stringListView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            addedTypes.remove(selectedItem);
        });

        menu.getItems().add(deleteButton);
        stringListView.setContextMenu(menu);

        stringListView.setItems(addedTypes);
        stringListView.setMaxHeight(150);
        fetchedTypes.setMinWidth(200);
        addTypeButton.setMinWidth(200);
        addTypeButton.setOnAction(event -> {
            final String selectedItem = fetchedTypes.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                System.out.println("Nothing is selected");
                return;
            }
            if (addedTypes.contains(selectedItem)) {
                System.out.println(selectedItem + " is already added");
                return;
            }
            addedTypes.add(selectedItem);
        });

        grid.setHgap(5);
        grid.setVgap(5);
        grid.addRow(0, new Label("Classroom name"), new Label("Classroom size"));
        grid.addRow(1, classroomName, classroomSize);
        grid.addRow(2, new Label("Types: "));
        grid.addRow(3, fetchedTypes, addTypeButton);
        grid.addRow(4, stringListView);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                if (
                        stringListView.getItems().isEmpty() ||
                                !classroomSize.getText().matches("\\d+") ||
                                classroomName.getText().isEmpty()
                ) {
                    adminViewModel.responseMessageProperty().setValue("Classroom types has to have minimum 1 type \n" +
                            "Classroom size has to be a number \n" +
                            "Classroom name has to be filled");
                    return null;
                }
                return new Classrooms(
                        null,
                        classroomName.getText(),
                        _addedTypes,
                        Integer.parseInt(classroomSize.getText())
                );
            }
            return null;
        });

        final Classrooms newClassroom = dialog.showAndWait().orElse(null);
        if (newClassroom == null) {
            return;
        }
        adminViewModel.addNewClassroom(newClassroom);
    }

    @FXML
    void refreshClassrooms() {
        adminViewModel.refreshClassrooms();
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
