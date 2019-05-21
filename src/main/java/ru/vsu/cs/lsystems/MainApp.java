package ru.vsu.cs.lsystems;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.vsu.cs.lsystems.view.MainView;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        MainView view = new MainView();
        primaryStage.setScene(new Scene(view, 700, 500));
        primaryStage.setTitle("L-System");
        primaryStage.setOnShowing(event -> view.refresh());
        primaryStage.show();
    }
}
