package com.company.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import com.company.model.*;
import com.company.model.buffers.CoursesArrayList;
import com.company.model.buffers.TeachersArrayList;
import com.company.transport.request.Request;
import com.company.transport.response.Response;
import com.company.view.CurrentUser;
import com.company.view.NodeWorker;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class StudentController implements DefaultController {

    public MenuItem menuItemShowAccount;
    public MenuItem menuItemEditAccount;
    public MenuItem menuItemDeleteAccount;
    public MenuItem menuItemShowMyCourses;
    public MenuItem menuItemShowAllCourses;
    public MenuItem menuItemShowAllTeachers;
    public MenuItem menuItemShowMyTeachers;
    public MenuItem menuItemInfoAboutCourses;
    public MenuItem menuItemWriteToAdmin;
    public MenuItem menuItemLogOut;

    public ImageView imageBack;
    public ImageView imageEdit;

    public Label labelTitle;
    public Label labelUsernameTop;
    public Label labelInfoAddCourse;

    public Label labelUsername;
    public Label labelPassword;
    public Label labelLastName;
    public Label labelFirstName;
    public Label labelMiddleName;
    public Label labelGender;
    public Label labelBirthdate;

    public Pane paneAccount;
    public Pane paneEditAccount;
    public Pane paneDeleteAccount;
    public Pane paneAllCourses;
    public Pane paneAllTeachers;
    public Pane paneMyTeachers;
    public Pane paneMyCourses;

    public TextField textLastName;
    public TextField textFirstName;
    public TextField textMiddleName;
    public TextField textUsername;
    public TextField textPassword;
    public PasswordField textPasswordToDeleteAccount;

    public DatePicker dateBirthdate;

    public Button buttonSubmitEditAccount;
    public Button buttonSubmitDeleteAccount;

    public ToggleButton toggleMale;
    public ToggleButton toggleFemale;

    public TableView<Course> tableAllCourses;
    public TableView<Course> tableMyCourses;
    public TableView<Teacher> tableAllTeachers;
    public TableView<Teacher> tableMyTeachers;

    public TableColumn columnAllCourseName;
    public TableColumn columnAllCourseLanguage;
    public TableColumn columnAllCourseLevel;
    public TableColumn columnAllCourseTeacher;
    public TableColumn columnAllCourseStartDate;
    public TableColumn columnAllCourseDuration;
    public TableColumn columnAllCourseCost;

    public TableColumn columnMyCourseName;
    public TableColumn columnMyCourseLanguage;
    public TableColumn columnMyCourseLevel;
    public TableColumn columnMyCourseTeacher;
    public TableColumn columnMyCourseStartDate;
    public TableColumn columnMyCourseDuration;
    public TableColumn columnMyCourseCost;

    public TableColumn columnAllTeachersLastName;
    public TableColumn columnAllTeachersFirstName;
    public TableColumn columnAllTeachersMiddleName;
    public TableColumn columnAllTeachersBirthdate;
    public TableColumn columnAllTeachersLanguage;
    public TableColumn columnMyTeachersLastName;
    public TableColumn columnMyTeachersFirstName;
    public TableColumn columnMyTeachersMiddleName;
    public TableColumn columnMyTeachersBirthdate;
    public TableColumn columnMyTeachersLanguage;
    public Pane paneWriteReport;
    public TextArea textReport;
    public AnchorPane paneMoreInfoAboutCourse;
    public TextArea textInfoAboutAllCourse;
    public Label labelCountOfMyCourse;
    public Label labelSumOfMyCourse;
    public Label labelCountOfAllCourses;

    @FXML
    protected void initialize() {
        hidePanes();
        actionShowAccount();
        setUserFields();
    }

    public void actionShowAllTeachers() {
        labelTitle.setText("Показать всех преподавателей");
        hidePanes();
        paneAllTeachers.setVisible(true);

        Teacher teacher = new Teacher();

        Request request = new Request(Request.GET, teacher,"all:");
        Response response = messageSender.sendRequestToServer(request);

        if (response.isSuccess()) {
            TeachersArrayList teachersArrayList = (TeachersArrayList) response.getModel();
            columnAllTeachersFirstName.setCellValueFactory(new PropertyValueFactory<Course, String>("firstName"));
            columnAllTeachersMiddleName.setCellValueFactory(new PropertyValueFactory<Course, String>("middleName"));
            columnAllTeachersLastName.setCellValueFactory(new PropertyValueFactory<Course, String>("lastName"));
            columnAllTeachersBirthdate.setCellValueFactory(new PropertyValueFactory<Course, String>("birthdate"));
            columnAllTeachersLanguage.setCellValueFactory(new PropertyValueFactory<Course, String>("languageName"));
            ObservableList<Teacher> courseObservableList = FXCollections.observableArrayList(teachersArrayList.getTeacherArrayList());
            tableAllTeachers.setItems(courseObservableList);
        } else {
            labelTitle.setText("Ошибка при загрузке преподавателей");
        }
    }

    public void actionShowMyTeachers() {
        labelTitle.setText("Показать моих преподавателей");
        hidePanes();
        paneMyTeachers.setVisible(true);

        Teacher teacher = new Teacher();

        Request request = new Request(Request.GET, teacher,"by_student_username:" + CurrentUser.getUser().getUsername());
        Response response = messageSender.sendRequestToServer(request);
        if (response.isSuccess()) {
            TeachersArrayList teachersArrayList = (TeachersArrayList) response.getModel();
            columnMyTeachersFirstName.setCellValueFactory(new PropertyValueFactory<Course, String>("firstName"));
            columnMyTeachersMiddleName.setCellValueFactory(new PropertyValueFactory<Course, String>("middleName"));
            columnMyTeachersLastName.setCellValueFactory(new PropertyValueFactory<Course, String>("lastName"));
            columnMyTeachersBirthdate.setCellValueFactory(new PropertyValueFactory<Course, String>("birthdate"));
            columnMyTeachersLanguage.setCellValueFactory(new PropertyValueFactory<Course, String>("languageName"));
            ObservableList<Teacher> courseObservableList = FXCollections.observableArrayList(teachersArrayList.getTeacherArrayList());
            tableMyTeachers.setItems(courseObservableList);
        } else {
            labelTitle.setText("Ошибка при загрузке преподавателей");
        }
    }

    public void actionShowMyCourses() {
        labelTitle.setText("Показать мои курсы");
        hidePanes();
        paneMyCourses.setVisible(true);

        Course course = new Course();

        Request request = new Request(Request.GET, course,"by_student_username:" + CurrentUser.getUser().getUsername());
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
            int countOfMyCourses = courseObservableList.size();
            double sumOfMyCourses = 0.0d;
            for (Course c: courseObservableList){
                sumOfMyCourses += c.getCost();
            }
            labelCountOfMyCourse.setText(String.valueOf(countOfMyCourses));
            labelSumOfMyCourse.setText(String.valueOf(sumOfMyCourses));
//            System.out.println("Count of my courses: " + countOfMyCourses);
//            System.out.println("Sum : " + sumOfMyCourses + " BYN");
        } else {
            labelTitle.setText(response.getMessage());
        }
    }

    public void actionShowAllCourses() {
        labelTitle.setText("Показать все курсы");
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
                int countOfAllCourses = courseObservableList.size();
                labelCountOfAllCourses.setText(String.valueOf(countOfAllCourses));
            }
            else {
                labelTitle.setText(response.getMessage());
            }
        } else {
            labelTitle.setText("Response is null");
        }
    }

    public void actionSubmitEditAccount() {

        TextInputControl [] textInputControls = {textFirstName, textLastName, textMiddleName, textUsername, textPassword};

        if (NodeWorker.textValidator(textInputControls)) {
            labelTitle.setText("Отправка");
            String firstName = textFirstName.getText();
            String middleName = textMiddleName.getText();
            String lastName = textLastName.getText();
            String username = textUsername.getText();
            String password = textPassword.getText();
            boolean isMale = toggleMale.isSelected();


            LocalDate localDate = dateBirthdate.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date birthdate = Date.from(instant);

            Student student = new Student(firstName,middleName,lastName,username,
                    password,birthdate,isMale);

            Request request = new Request(Request.PUT, student);
            Response response = messageSender.sendRequestToServer(request);

            if (response.isSuccess()) {
                student = (Student) response.getModel();
                CurrentUser.updateCurrentStudent(student);
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

    public void actionSubmitAddCourse() {
        Course course = tableAllCourses.getSelectionModel().getSelectedItem();
        Order order = new Order((Student) CurrentUser.getUser(), course);

        if (course!= null) {
            Request request = new Request(Request.POST, order);
            Response response = messageSender.sendRequestToServer(request);
            if (response.isSuccess()) {
                actionShowMyCourses();
            } else {
                labelTitle.setText("Ошибка при добавлении записи");
            }
        }
        else NodeWorker.animateNode(labelInfoAddCourse);
    }

    public void actionSubmitDeleteCourse() {
        Course course = tableMyCourses.getSelectionModel().getSelectedItem();
        Order order = new Order((Student) CurrentUser.getUser(), course);

        if (course!= null) {
            Request request = new Request(Request.DELETE, order);
            Response response = messageSender.sendRequestToServer(request);
            if (response.isSuccess()) {
                actionShowMyCourses();
            } else {
                labelTitle.setText("Ошибка при отписки от курса");
            }
        }
        else NodeWorker.animateNode(labelInfoAddCourse);
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

    private void setUserFields() {
        labelUsernameTop.setText(CurrentUser.getUser().getUsername());
        labelFirstName.setText(CurrentUser.getUser().getFirstName());
        labelMiddleName.setText(CurrentUser.getUser().getMiddleName());
        labelLastName.setText(CurrentUser.getUser().getLastName());
        labelUsername.setText(CurrentUser.getUser().getUsername());
        labelPassword.setText(CurrentUser.getUser().getPassword());
        labelBirthdate.setText(CurrentUser.getUser().getBirthdate().toString());
        labelGender.setText(CurrentUser.getUser().isMale()? "Мужской" : "Женский");

        textFirstName.setText(CurrentUser.getUser().getFirstName());
        textMiddleName.setText(CurrentUser.getUser().getMiddleName());
        textLastName.setText(CurrentUser.getUser().getLastName());
        textUsername.setText(CurrentUser.getUser().getUsername());
        textPassword.setText(CurrentUser.getUser().getPassword());
//        Date date = CurrentUser.getUser().getBirthdate();
//        LocalDate localDate = LocalDate.from(Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()));
//        dateBirthdate.setValue(localDate);
        NodeWorker.setDefaultDate(dateBirthdate);
        toggleMale.setSelected(CurrentUser.getUser().isMale());
        toggleFemale.setSelected(!CurrentUser.getUser().isMale());
    }

    private void hidePanes() {
        paneAccount.setVisible(false);
        paneEditAccount.setVisible(false);
        paneDeleteAccount.setVisible(false);
        paneAllCourses.setVisible(false);
        paneMyCourses.setVisible(false);
        paneAllTeachers.setVisible(false);
        paneMyTeachers.setVisible(false);
        paneWriteReport.setVisible(false);
        imageBack.setVisible(true);
        paneMoreInfoAboutCourse.setVisible(false);
    }

    public void actionWriteReport(ActionEvent actionEvent) {
        paneWriteReport.setVisible(true);
    }

    public void actionSubmitReport(ActionEvent actionEvent) {
        TextInputControl[] nodes = {textReport};
        if (NodeWorker.textValidator(nodes)){
            String text = textReport.getText();
            Student student = (Student) CurrentUser.getUser();
            Request request = new Request(Request.POST, new HelpStudent(student, text));

            Response response = messageSender.sendRequestToServer(request);

            if (response.isSuccess()) {
                labelTitle.setText("Благодарим за сообщение!");
                paneWriteReport.setVisible(false);
                textReport.setText("");
            } else {
                labelTitle.setText("Ваше сообщение не было отправлено");
                paneWriteReport.setVisible(false);
            }
        }

    }

    public void actionCloseMoreInfoAboutCourse(ActionEvent actionEvent) {
        paneMoreInfoAboutCourse.setVisible(false);
        textInfoAboutAllCourse.setText("");
    }
}