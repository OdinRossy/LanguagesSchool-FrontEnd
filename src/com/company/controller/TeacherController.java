package com.company.controller;

import com.company.model.TeacherStatistics;
import com.company.model.buffers.TeacherStatisticsArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

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
    public Label labelCountOfMyStudents;
    public Pane paneEditAccount;
    public Pane paneDeleteAccount;
    public PasswordField textPasswordToDeleteAccount;
    public Button buttonSubmitDeleteAccount;
    public Pane paneAllCourses;
    public TableView<Course> tableAllCourses;
    public TableColumn columnAllCourseName;
    public TableColumn columnAllCourseTeacher;
    public TableColumn columnAllCourseLanguage;
    public TableColumn columnAllCourseLevel;
    public TableColumn columnAllCourseDuration;
    public TableColumn columnAllCourseStartDate;
    public TableColumn columnAllCourseCost;
    public Pane paneMyCourses;
    public TableView<Course> tableMyCourses;
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
    public AnchorPane paneMoreInfoAboutCourse;
    public AnchorPane paneMoreInfoAboutMyCourse;
    public TextArea textInfoAboutMyCourse;
    public TextArea textInfoAboutAllCourse;
    public Pane paneMyStatistics;
    public PieChart pieMyStatistics;

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

    public void actionShowStatistics() {
        labelTitle.setText("Статистика по курсам");
        hidePanes();
        paneMyStatistics.setVisible(true);

        Request request = new Request(Request.GET,new TeacherStatistics(CurrentUser.getUser().getUsername()));
        Response response = messageSender.sendRequestToServer(request);

        TeacherStatisticsArrayList teacherStatisticsArrayList = (TeacherStatisticsArrayList) response.getModel();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (int i = 0; i < teacherStatisticsArrayList.getTeacherStatisticsArrayList().size(); i++) {
            pieChartData.add(i,new PieChart.Data(teacherStatisticsArrayList.getTeacherStatisticsArrayList().get(i).getNameOfCourse(),
                    teacherStatisticsArrayList.getTeacherStatisticsArrayList().get(i).getStudentsCount()));
        }
        pieMyStatistics.setData(pieChartData);
        pieMyStatistics.setTitle("Статистика по моим курсам");
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
            request = new Request(Request.GET,new Student(), "count_by_username:" + CurrentUser.getUser().getUsername());
            response = messageSender.sendRequestToServer(request);

            int countOfMyStudents = Integer.parseInt(response.getMessage());
            labelCountOfMyStudents.setText(String.valueOf(countOfMyStudents));
        } else {
            labelTitle.setText(response.getMessage());
        }
    }

    public void actionMoreInfoAboutMyCourse() {
        paneMoreInfoAboutMyCourse.setVisible(true);
        Course course = tableMyCourses.getSelectionModel().getSelectedItem();
        String text = "Название: " + course.getNameOfCourse() + "\n" +
                "Преподаватель: " + course.getTeacher()  + "\n" +
                "Язык: "  + course.getLanguage() + "\n" +
                "Уровень: "  + course.getLevel() + "\n" +
                "Дата начала: "  + course.getStartDate() + "\n" +
                "Стоимость: "  + course.getCost() + " BYN.";
        textInfoAboutMyCourse.setText(text);
        paneMoreInfoAboutMyCourse.setVisible(true);
    }

    public void actionShowMoreInfoAboutCourse() {
        Course course = tableAllCourses.getSelectionModel().getSelectedItem();
        String text = "Название: " + course.getNameOfCourse() + "\n" +
                "Преподаватель: " + course.getTeacher()  + "\n" +
                "Язык: "  + course.getLanguage() + "\n" +
                "Уровень: "  + course.getLevel() + "\n" +
                "Дата начала: "  + course.getStartDate() + "\n" +
                "Стоимость: "  + course.getCost() + " BYN.";
        textInfoAboutAllCourse.setText(text);
        paneMoreInfoAboutCourse.setVisible(true);
    }

    public void actionCloseMoreInfoAboutCourse() {
        paneMoreInfoAboutCourse.setVisible(false);
        textInfoAboutAllCourse.setText("");
    }

    public void actionSubmitAddMoreInfoAboutCourse() {
        paneMoreInfoAboutMyCourse.setVisible(false);
        textInfoAboutMyCourse.setText("");
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

    public void actionSubmitDeleteAccount() {
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
        labelBirthdate.setText(teacher.getBirthdate().toString());
        labelGender.setText(teacher.isMale()? "Мужской" : "Женский");
        labelSalary.setText(String.valueOf(teacher.getSalary()));
        labelLanguage.setText(teacher.getLanguage());

        textFirstName.setText(CurrentUser.getUser().getFirstName());
        textMiddleName.setText(CurrentUser.getUser().getMiddleName());
        textLastName.setText(CurrentUser.getUser().getLastName());
        textUsername.setText(CurrentUser.getUser().getUsername());
        textPassword.setText(CurrentUser.getUser().getPassword());
        Date date = CurrentUser.getUser().getBirthdate();
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
        paneMoreInfoAboutCourse.setVisible(false);
        paneMoreInfoAboutMyCourse.setVisible(false);
        paneMyStatistics.setVisible(false);
        imageBack.setVisible(true);
    }
}
