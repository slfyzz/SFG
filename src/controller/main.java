package controller;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.GUI;



public class main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Calculator");
        Pane root = GUI.getGUIform(400,300);
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToHeight(true);
        BorderPane mainRoot = new BorderPane();
        mainRoot.setLeft(scrollPane);

        root.setPadding(new Insets(15));
        mainRoot.setCenter(GUI.getResult(400, 300));
        mainRoot.getCenter().minWidth(100);
        primaryStage.setScene(new Scene(mainRoot, 600, 600));
        //   primaryStage.setResizable(false);
        primaryStage.show();
    }
}
