package com.company.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import com.company.model.Course;
import com.company.model.Student;
import com.company.model.Teacher;
import com.company.model.buffers.CoursesArrayList;
import com.company.model.buffers.StudentsArrayList;
import com.company.transport.request.Request;
import com.company.transport.response.Response;
import com.company.view.CurrentUser;
import com.company.view.NodeWorker;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class TeacherController implements DefaultController {
    public ImageView imageBack;
    public Label labelTitle;
    public Label labelUsernameTop;
    public Pane paneAccount;
    public ImageView imageEdit;
    public Label labelLastName;
    public Label labelFirstName;
    public Label labelMiddleName;
    public Label labelGender;
    public Label labelBirthdate;
    public Label labelUsername;
    public Label labelPassword;
    public Label labelLanguage;
    public Label labelSalary;
    public Pane paneEditAccount;
    public Pane paneDeleteAccount;
    public PasswordField textPasswordToDeleteAccount;
    public Button buttonSubmitDeleteAccount;
    public Pane paneAllCourses;
    public TableView tableAllCourses;
    public TableColumn columnAllCourseName;
    public TableColumn columnAllCourseTeacher;
    public TableColumn columnAllCourseLanguage;
    public TableColumn columnAllCourseLevel;
    public TableColumn columnAllCourseDuration;
    public TableColumn columnAllCourseStartDate;
    public TableColumn columnAllCourseCost;
    public Pane paneMyCourses;
    public TableView tableMyCourses;
    public TableColumn columnMyCourseName;
    public TableColumn columnMyCourseTeacher;
    public TableColumn columnMyCourseLanguage;
    public TableColumn columnMyCourseLevel;
    public TableColumn columnMyCourseDuration;
    public TableColumn columnMyCourseStartDate;
    public TableColumn columnMyCourseCost;
    public Pane paneMyStudents;
    public TextField textPassword;
    public TextField textUsername;
    public DatePicker dateBirthdate;
    public ToggleButton toggleMale;
    public TextField textMiddleName;
    public TextField textFirstName;
    public TextField textLastName;
    public ToggleButton toggleFemale;

    public TableView tableMyStudents;
    public TableColumn columnMyStudentsCourse;
    public TableColumn columnMyStudentsLastName;
    public TableColumn columnMyStudentsFirstName;
    public TableColumn columnMyStudentsMiddleName;
    public TableColumn columnMyStudentsBirthdate;

    @FXML
    protected void initialize() {
        hidePanes();
        actionShowAccount();
        setUserFields();
    }

    public void actionShowAccount() {
        labelTitle.setText("Мой аккаунт");
        hidePanes();
        setUserFields();
        paneAccount.setVisible(true);
        imageBack.setVisible(false);
    }

    public void actionEditAccount() {
        labelTitle.setText("Редактировать аккаунт");
        hidePanes();
        setUserFields();
        paneEditAccount.setVisible(true);
    }

    public void actionDeleteAccount() {
        labelTitle.setText("Удалить аккаунт");
        hidePanes();
        paneDeleteAccount.setVisible(true);
    }

    public void actionShowCourse() {
    }

    public void actionShowMyCourses() {
        labelTitle.setText("Мои курсы");
        hidePanes();
        paneMyCourses.setVisible(true);

        Course course = new Course();

        Request request = new Request(Request.GET, course,"by_teacher_username:" + CurrentUser.getUser().getUsername());
        Response response = messageSender.sendRequestToServer(request);

        if (response.isSuccess()) {
            CoursesArrayList courseArrayList = (CoursesArrayList) response.getModel();
            columnMyCourseName.setCellValueFactory(new PropertyValueFactory<Course, String>("nameOfCourse"));
            columnMyCourseLanguage.setCellValueFactory(new PropertyValueFactory<Course, String>("language"));
            columnMyCourseLevel.setCellValueFactory(new PropertyValueFactory<Course, String>("level"));
            columnMyCourseTeacher.setCellValueFactory(new PropertyValueFactory<Course, String>("teacher"));
            columnMyCourseStartDate.setCellValueFactory(new PropertyValueFactory<Course, String>("startDate"));
            columnMyCourseDuration.setCellValueFactory(new PropertyValueFactory<Course, String>("duration"));
            columnMyCourseCost.setCellValueFactory(new PropertyValueFactory<Course, Double>("cost"));
            ObservableList<Course> courseObservableList = FXCollections.observableArrayList(courseArrayList.getCourseArrayList());
            tableMyCourses.setItems(courseObservableList);
        } else {
            labelTitle.setText(response.getMessage());
        }
    }

    public void actionShowAllCourses() {
        labelTitle.setText("Все курсы");
        hidePanes();
        paneAllCourses.setVisible(true);

        Course course = new Course();

        Request request = new Request(Request.GET, course,"all:");
        Response response = messageSender.sendRequestToServer(request);

        if (response != null) {
            if (response.isSuccess()) {
                CoursesArrayList courseArrayList = (CoursesArrayList) response.getModel();
                columnAllCourseName.setCellValueFactory(new PropertyValueFactory<Course, String>("nameOfCourse"));
                columnAllCourseLanguage.setCellValueFactory(new PropertyValueFactory<Course, String>("language"));
                columnAllCourseLevel.setCellValueFactory(new PropertyValueFactory<Course, String>("level"));
                columnAllCourseTeacher.setCellValueFactory(new PropertyValueFactory<Course, String>("teacher"));
                columnAllCourseStartDate.setCellValueFactory(new PropertyValueFactory<Course, String>("startDate"));
                columnAllCourseDuration.setCellValueFactory(new PropertyValueFactory<Course, String>("duration"));
                columnAllCourseCost.setCellValueFactory(new PropertyValueFactory<Course, Double>("cost"));
                ObservableList<Course> courseObservableList = FXCollections.observableArrayList(courseArrayList.getCourseArrayList());
                tableAllCourses.setItems(courseObservableList);
            }
            else {
                labelTitle.setText(response.getMessage());
            }
        } else {
            labelTitle.setText("Response is null");
        }

    }

    public void actionSearchCourse() {
    }

    public void actionShowMyStudents() {
        labelTitle.setText("Мои студенты");
        hidePanes();
        paneMyStudents.setVisible(true);

        Student student = new Student();

        Request request = new Request(Request.GET, student,"by_teacher_username:" + CurrentUser.getUser().getUsername());
        Response response = messageSender.sendRequestToServer(request);

        if (response.isSuccess()) {
            StudentsArrayList studentsArrayList = (StudentsArrayList) response.getModel();
            columnMyStudentsFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
            columnMyStudentsLastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
            columnMyStudentsMiddleName.setCellValueFactory(new PropertyValueFactory<Student, String>("middleName"));
            columnMyStudentsBirthdate.setCellValueFactory(new PropertyValueFactory<Student, String>("birthdate"));
            columnMyStudentsCourse.setCellValueFactory(new PropertyValueFactory<Student, String>("additionInfo"));
//            columnMyCourseDuration.setCellValueFactory(new PropertyValueFactory<Student, String>("duration"));
//            columnMyCourseCost.setCellValueFactory(new PropertyValueFactory<Student, Double>("cost"));
            ObservableList<Student> courseObservableList = FXCollections.observableArrayList(studentsArrayList.getStudentArrayList());
            tableMyStudents.setItems(courseObservableList);
        } else {
            labelTitle.setText(response.getMessage());
        }
    }

    public void actionLogOut() {
        labelTitle.setText("Выйти");
        NodeWorker.closeWindow(labelTitle);
        CurrentUser.removeCurrentStudent();
    }

    public void showImageBack() {
        imageBack.setOpacity(0.6);
    }

    public void hideImageBack() {
        imageBack.setOpacity(0.25);
    }

    public void showImageEdit() {
        imageEdit.setOpacity(0.6);
    }

    public void hideImageEdit() {
        imageEdit.setOpacity(0.25);
    }

    public void actionSubmitEditAccount() {
        TextInputControl [] textInputControls = {textFirstName, textLastName, textMiddleName, textUsername, textPassword};

        if (NodeWorker.textValidator(textInputControls)) {
            labelTitle.setText("Отправка");

            LocalDate localDate = dateBirthdate.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date birthdate = Date.from(instant);

            Teacher teacher = (Teacher) CurrentUser.getUser();
            teacher.setFirstName(textFirstName.getText());
            teacher.setMiddleName(textMiddleName.getText());
            teacher.setPassword(textPassword.getText());
            teacher.setMale( toggleMale.isSelected());
            teacher.setBirthdate(birthdate);

            Request request = new Request(Request.PUT, teacher);
            Response response = messageSender.sendRequestToServer(request);

            if (response.isSuccess()) {
                teacher = (Teacher) response.getModel();
                CurrentUser.updateCurrentStudent(teacher);
                labelTitle.setText("Информация обновлена");
                actionShowAccount();
            } else {
                labelTitle.setText(response.getMessage());
            }
        } else {
            labelTitle.setText("Поля введены некорректно!");
        }
        setUserFields();
    }

    public void actionSubmitDeleteAccount(ActionEvent actionEvent) {
        if (CurrentUser.getUser().getPassword().equals(textPasswordToDeleteAccount.getText())) {
            Request request = new Request(Request.DELETE, CurrentUser.getUser());
            messageSender.sendRequestToServer(request);

            actionLogOut();
        } else {
            NodeWorker.animateNode(textPasswordToDeleteAccount);
        }
    }

    private void setUserFields() {
        Teacher teacher = (Teacher) CurrentUser.getUser();
        labelUsernameTop.setText(teacher.getUsername());
        labelFirstName.setText(teacher.getFirstName());
        labelMiddleName.setText(teacher.getMiddleName());
        labelLastName.setText(teacher.getLastName());
        labelUsername.setText(teacher.getUsername());
        labelPassword.setText(teacher.getPassword());
        labelBirthdate.setText((String) teacher.getBirthdate("String"));
        labelGender.setText(teacher.isMale()? "Мужской" : "Женский");
        labelSalary.setText(String.valueOf(teacher.getSalary()));
        labelLanguage.setText(teacher.getLanguage());

        textFirstName.setText(CurrentUser.getUser().getFirstName());
        textMiddleName.setText(CurrentUser.getUser().getMiddleName());
        textLastName.setText(CurrentUser.getUser().getLastName());
        textUsername.setText(CurrentUser.getUser().getUsername());
        textPassword.setText(CurrentUser.getUser().getPassword());
        Date date = (Date) CurrentUser.getUser().getBirthdate("Date");
        LocalDate localDate = LocalDate.from(Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()));
        dateBirthdate.setValue(localDate);
        toggleMale.setSelected(CurrentUser.getUser().isMale());
        toggleFemale.setSelected(!CurrentUser.getUser().isMale());
    }

    private void hidePanes() {
        paneAccount.setVisible(false);
        paneEditAccount.setVisible(false);
        paneDeleteAccount.setVisible(false);
        paneAllCourses.setVisible(false);
        paneMyCourses.setVisible(false);
        paneMyStudents.setVisible(false);
        imageBack.setVisible(true);
    }

    public void actionAddMoreInfoAboutCourse(ActionEvent actionEvent) {
    }

    public void actionShowMoreInfoAboutCourse(ActionEvent actionEvent) {
    }
}
