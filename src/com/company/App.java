package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.company.view.ViewConfiguration;

import java.io.File;
import java.net.URL;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        final String FILE_NAME = "index";
        final String PATH_TO_FXML = ViewConfiguration.PATH_TO_FXML + "/" + FILE_NAME + ViewConfiguration.FXML_EXTENSION;
        URL url = new File(PATH_TO_FXML).toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Добро пожаловать!");
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

