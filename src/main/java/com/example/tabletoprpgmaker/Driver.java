package com.example.tabletoprpgmaker;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;


public class Driver extends Application {

    HashMap<String, Component> components = new HashMap<>();

    @Override
    public void start(Stage window) {

        Label label = new Label("Hello, JavaFX!");

        VBox vBox = new VBox(10);



        TextField titleInput = new TextField();
        titleInput.setMaxWidth(200);
        TextField descInput = new TextField();
        descInput.setMaxWidth(200);

        Button createButton = new Button("Create");
        createButton.setOnAction(e -> {
            Component component = new Component(titleInput.getText());
            component.setDescription(descInput.getText());
            components.put(titleInput.getText(), component);
            titleInput.clear();
            descInput.clear();
        });

        TextField componentNameInput = new TextField();
        componentNameInput.setMaxWidth(200);
        TextField nameInput = new TextField();
        nameInput.setMaxWidth(200);
        TextField valueInput = new TextField();
        valueInput.setMaxWidth(200);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            Component component = components.get(componentNameInput.getText());
            component.addProperty(nameInput.getText(), valueInput.getText());
            nameInput.clear();
            valueInput.clear();
        });

        TextField componentNameInput1 = new TextField();
        componentNameInput1.setMaxWidth(200);
        Button printButton = new Button("Print");
        printButton.setOnAction(e -> components.get(componentNameInput1.getText()).printProperties());

        vBox.getChildren().addAll(titleInput, descInput, createButton, componentNameInput, nameInput, valueInput, addButton, componentNameInput1, printButton);

        Scene scene = new Scene(vBox, 400, 600);
        window.setScene(scene);
        window.setTitle("JavaFX App");
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
