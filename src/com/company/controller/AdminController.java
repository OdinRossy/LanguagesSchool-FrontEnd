package com.company.controller;

import com.company.model.Language;
import com.company.model.buffers.LanguagesArrayList;
import com.company.transport.request.Request;
import com.company.transport.response.Response;
import com.company.view.NodeWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class AdminController implements DefaultController {
    public Label labelTitle;
    public Label labelUsernameTop;

    public Pane paneStatistics;
    public Pane paneShowAllCourses;
    public Pane paneAddCourse;
    public Pane paneDeleteCourse;

    public Pane paneShowAllTeachers;
    public Pane paneAddTeacher;
    public Pane paneDeleteTeacher;

    public Pane paneShowAllStudents;
    public Pane paneAddStudent;
    public Pane paneDeleteStudent;
    public TextField textLastName;
    public TextField textFirstName;
    public TextField textMiddleName;
    public DatePicker dateBirthdate;
    public TextField textUsername;
    public TextField textPassword;
    public ToggleButton toggleMale;
    public ToggleButton toggleFemale;
    public TextField textSalary;
    public ComboBox comboLanguage;

    @FXML
    protected void initialize() {
        actionShowStatistics();
    }

    public void actionShowStatistics() {
        hidePanes();
        labelTitle.setText("Главная");
        paneStatistics.setVisible(true);
    }

    public void actionShowAllCourses() {
        hidePanes();
        labelTitle.setText("Показать все курсы");
        paneShowAllCourses.setVisible(true);
    }

    public void actionAddCourse() {
        hidePanes();
        labelTitle.setText("Добавить курс");
        paneAddCourse.setVisible(true);
    }

    public void actionDeleteCourse() {
        hidePanes();
        labelTitle.setText("Удалить курс");
        paneDeleteCourse.setVisible(true);
    }

    public void actionShowAllTeachers() {
        hidePanes();
        labelTitle.setText("Показать всех преподавателей");
        paneShowAllTeachers.setVisible(true);
    }

    public void actionAddTeacher() {
        hidePanes();
        labelTitle.setText("Добавить преподавателя");
        paneAddTeacher.setVisible(true);

        NodeWorker.setDefaultDate(dateBirthdate);

        Request request = new Request(Request.GET, new Language());
        Response response = messageSender.sendRequestToServer(request);

        LanguagesArrayList languagesArrayList = (LanguagesArrayList) response.getModel();
        ObservableList observableList = FXCollections.observableList(languagesArrayList.getLanguageArrayList());

        comboLanguage.setItems(observableList);
    }

    public void actionDeleteTeacher() {
        hidePanes();
        labelTitle.setText("Удалить преподвателя");
        paneDeleteTeacher.setVisible(true);
    }

    public void actionShowAllStudents() {
        hidePanes();
        labelTitle.setText("Показать всех студентов");
        paneShowAllStudents.setVisible(true);
    }

    public void actionDeleteStudent() {
        hidePanes();
        labelTitle.setText("Удалить студента");
        paneDeleteStudent.setVisible(true);
    }

    public void actionLogOut() {

    }

    public void actionShowAccount() {
        actionShowStatistics();
    }

    public void hideImageBack() {

    }

    public void showImageBack() {

    }

    public void actionStopServer() {
        hidePanes();
        labelTitle.setText("Cервер отключен, любые последующие изменения не будут сохранены");
    }

    public void actionSubmitAddTeacher() {

    }

    private void hidePanes() {
        labelTitle.setText("");
        paneStatistics.setVisible(false);
        paneShowAllCourses.setVisible(false);
        paneAddCourse.setVisible(false);
        paneDeleteCourse.setVisible(false);
        paneShowAllTeachers.setVisible(false);
        paneAddTeacher.setVisible(false);
        paneDeleteTeacher.setVisible(false);
        paneShowAllStudents.setVisible(false);
        paneAddStudent.setVisible(false);
        paneDeleteStudent.setVisible(false);
    }
}