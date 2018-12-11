package com.company.view;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextInputControl;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class NodeWorker implements ViewConfiguration {

    public static void animateNode(Node node) {

        TranslateTransition transition = new TranslateTransition(Duration.millis(50), node);

        transition.setFromX(0);
        transition.setFromY(0);

        transition.setToX(2.5);
        transition.setToY(2.5);

        transition.setCycleCount(6);
        transition.setAutoReverse(true);

        transition.playFromStart();

    }

    public static void animateNodes(Node [] nodes) {

        for (Node node : nodes) {
            animateNode(node);
        }
    }

    public static boolean textValidator(TextInputControl [] texts) {
        boolean isFieldsValid = true;

        for (TextInputControl textInputControl: texts) {
            if (NodeWorker.isTextEmpty(textInputControl)){
                NodeWorker.animateNode(textInputControl);
                isFieldsValid = false;
            }
        }
        return isFieldsValid;
    }

    public static void openWindow(String filename, String Window_Title, ActionEvent actionEvent) {
        final String PATH_TO_FXML = ViewConfiguration.PATH_TO_FXML + "/" + filename + ViewConfiguration.FXML_EXTENSION;

        try {
            URL url = new File(PATH_TO_FXML).toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Stage stage = new Stage();
            stage.setTitle(Window_Title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.setResizable(false);
            stage.sizeToScene();
            stage.show();
            // Hide this current window (if this is what you want)
//            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeWindow(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    private static boolean isTextEmpty(TextInputControl text) {
        return text.getText().trim().equals("") && text.getText().trim().length() == 0;
    }

    public static void setDefaultDate(DatePicker datePicker) {
        Date date = new Date();
        LocalDate localDate = LocalDate.from(Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()));
        datePicker.setValue(localDate);
    }

}
