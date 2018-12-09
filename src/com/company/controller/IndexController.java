package com.company.controller;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import com.company.model.Student;
import com.company.model.Teacher;
import com.company.transport.request.Request;
import com.company.transport.response.Response;
import com.company.view.CurrentUser;
import com.company.view.NodeWorker;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class IndexController implements DefaultController {

    public JFXTextField textUsername;
    public JFXPasswordField textPassword;
    public JFXButton buttonSubmitSignIn;
    public JFXRadioButton radioIsStudent;
    public JFXButton buttonChoiceSignIn;
    public JFXButton buttonChoiceSignUp;

    public ImageView imageBack;
    public ImageView imageBackground;

    public Pane paneSignIn;
    public Pane paneMain;
    public Pane paneImage;
    public Pane paneSignUpStudent;
    public Pane paneSignUpTeacher;

    public JFXTextField textLastNameStudentSignUp;
    public JFXTextField textFirstNameStudentSignUp;
    public JFXTextField textMiddleNameStudentSignUp;
    public JFXTextField textUsernameStudentSignUp;
    public JFXPasswordField textPasswordStudentSignUp;
    public JFXDatePicker dateBirthdaySignUpStudent;
    public RadioButton toggleIsMaleSignUpStudent;

    private short currentImage;
    private short countImages = 11;

    private boolean isStudent;

    private final String onButtonMouseEntered = "-fx-border-color:#333; -fx-border-radius: 7; -fx-border-width: 2; " +
            "-fx-background-radius: 7; -fx-background-color: #333";
    private final String onButtonMouseExited = "-fx-border-color:#333; -fx-border-radius: 7; -fx-border-width: 2; " +
            "-fx-background-radius: 7";
    @FXML
    protected void initialize() {
        hidePanes();
        setRandomBackground();
        changeChoiceIsStudent();
        actionShowMainPane();
    }

    public void actionShowMainPane() {
        actionChangeBackground();
        hidePanes();
        imageBack.setVisible(false);
        paneMain.setVisible(true);
        paneImage.setVisible(true);
    }

    public void actionChoiceSignIn() {
        hidePanes();
        paneSignIn.setVisible(true);
        imageBack.setVisible(true);
        paneImage.setVisible(true);
    }

    public void actionChoiceSignUp() {
        setDefaultDate(dateBirthdaySignUpStudent);
        actionChangeBackground();
        hidePanes();
        paneImage.setVisible(true);
        paneSignUpStudent.setVisible(isStudent);
//        paneSignUpTeacher.setVisible(!isStudent);
        imageBack.setVisible(true);
    }

    public void actionSubmitSignUp(ActionEvent actionEvent) throws ParseException {
        if (isStudent) {
            TextInputControl[] texts = {textFirstNameStudentSignUp, textLastNameStudentSignUp,
                    textMiddleNameStudentSignUp,textUsernameStudentSignUp, textPasswordStudentSignUp};

            if (NodeWorker.textValidator(texts)) {
                String firstName = textFirstNameStudentSignUp.getText();
                String lastName = textLastNameStudentSignUp.getText();
                String middleName = textMiddleNameStudentSignUp.getText();
                String username = textUsernameStudentSignUp.getText();

                LocalDate localDate = dateBirthdaySignUpStudent.getValue();
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                Date birthdate = Date.from(instant);

                String password = textPasswordStudentSignUp.getText();
                boolean isMale = toggleIsMaleSignUpStudent.isSelected();

                Student student = new Student(firstName,middleName,lastName,username,password,birthdate,isMale);
                signUpAsStudent(actionEvent, student);

            } else {

            }

        } else {
//            Sign Up as teacher
        }
    }

    public void actionSubmitSignIn(ActionEvent actionEvent) {

        TextInputControl[] texts = {textUsername, textPassword};

        if (NodeWorker.textValidator(texts)) {
            String username = textUsername.getText();
            String password = textPassword.getText();

            if (isStudent) {
                signInAsStudent(actionEvent, new Student(username,password));
            } else {
                signInAsTeacher(actionEvent, new Teacher(username,password));
            }
        } else {
        }
        clearTexts();
    }

    private void signInAsStudent(ActionEvent actionEvent, Student student) {
        System.out.println("Sign in as student");
        Request request = new Request(Request.GET, student, "sign_in:");
        Response response = messageSender.sendRequestToServer(request);

        if (response != null) {
            if (response.isSuccess()) {
                student = (Student) response.getModel();
                CurrentUser.setUser(student);
                NodeWorker.openWindow("mainStudent", student.getUsername(), 1000, 600, actionEvent);
                actionShowMainPane();

            } else {
                Node[] nodes = {textUsername, textPassword};
                NodeWorker.animateNodes(nodes);
            }
        } else {
            Node[] nodes = {textUsername, textPassword};
            NodeWorker.animateNodes(nodes);
        }
    }

    private void signUpAsStudent(ActionEvent actionEvent, Student student) {

        Request request = new Request(Request.POST, student, "sign_up:");
        Response response = messageSender.sendRequestToServer(request);

        if (response != null) {
            if (response.isSuccess()) {
                signInAsStudent(actionEvent, student);
            } else {
                NodeWorker.animateNode(textUsernameStudentSignUp);
            }
        } else {
        }
    }

    private void signInAsTeacher(ActionEvent actionEvent, Teacher teacher) {
        System.out.println("Sign in as teacher");
        hidePanes();
        Request request = new Request(Request.GET, teacher, "sign_in:");
        Response response = messageSender.sendRequestToServer(request);

        if (response != null) {
            if (response.isSuccess()) {
                teacher = (Teacher) response.getModel();
                CurrentUser.setUser(teacher);
                NodeWorker.openWindow("mainTeacher", teacher.getUsername(), 1000, 600, actionEvent);
                actionShowMainPane();

            } else {
                paneSignIn.setVisible(true);
                paneImage.setVisible(true);
                System.out.println("Is success : " + response.isSuccess());
                Node[] nodes = {textUsername, textPassword};
                NodeWorker.animateNodes(nodes);
            }
        }
    }

    private void signUpAsTeacher() {

    }

    public void changeChoiceIsStudent() {
        isStudent = radioIsStudent.isSelected();
        if (!isStudent) {
            buttonChoiceSignUp.setVisible(false);
        } else {
            buttonChoiceSignUp.setVisible(true);
        }
    }

    private void hidePanes() {
        clearTexts();
        paneMain.setVisible(false);
        paneSignIn.setVisible(false);
        paneImage.setVisible(false);
        paneSignUpStudent.setVisible(false);
//        paneSignUpTeacher.setVisible(false);
    }

    public void actionChoiceSignInMouseEntered() {
        buttonChoiceSignIn.setStyle(onButtonMouseEntered);
    }

    public void actionChoiceSignInMouseExited() {
        buttonChoiceSignIn.setStyle(onButtonMouseExited);
    }

    public void actionButtonChoiceSignUpMouseEntered() {
        buttonChoiceSignUp.setStyle(onButtonMouseEntered);
    }

    public void actionButtonChoiceSignUpMouseExited() {
        buttonChoiceSignUp.setStyle("");
    }

    public void actionSubmitSignInMouseEntered() {
        buttonSubmitSignIn.setStyle(onButtonMouseEntered);
    }

    public void actionSubmitSignInMouseExited() {
        buttonSubmitSignIn.setStyle(onButtonMouseExited);
    }

    private void setRandomBackground() {
        currentImage = (short) (Math.random() * countImages);
        setBackground();
    }

    private void setBackground() {
        if (currentImage > countImages) {
            currentImage = 0;
        }

        String name = currentImage + ".png";
        URL url = null;
        try {
            url = new File("src/resources/assets/backgrounds/" + name).toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        imageBackground.setImage(new Image(url.toString()));
    }

    public void actionChangeBackground() {
        currentImage++;
        setBackground();
    }

    private void clearTexts() {
        textUsername.setText("");
        textPassword.setText("");
        textFirstNameStudentSignUp.setText("");
        textMiddleNameStudentSignUp.setText("");
        textLastNameStudentSignUp.setText("");
        textUsernameStudentSignUp.setText("");
        textPasswordStudentSignUp.setText("");
        setDefaultDate(dateBirthdaySignUpStudent);
    }

    private void setDefaultDate(DatePicker datePicker) {
        Date date = new Date();
        LocalDate localDate = LocalDate.from(Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()));
        datePicker.setValue(localDate);
    }
}
