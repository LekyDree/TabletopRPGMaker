package com.example.tabletoprpgmaker;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.HashMap;


public class Driver extends Application {

    private static final HashMap<String, Component> components = new HashMap<>();

    @Override
    public void start(Stage window) {

        VBox createBox = getCreateBox();
        VBox addBox = getAddBox();
        VBox bindBox = getBindBox();

        TextField printInput = new TextField();
        printInput.setMaxWidth(200);
        Button printButton = new Button("Print");
        printButton.setOnAction(e -> components.get(printInput.getText()).printAllProperties());
        VBox printBox = new VBox(printInput, printButton);
        printBox.setSpacing(10);

        TextArea textArea = new TextArea("This is a label-like text area. You can click and drag to highlight this text.");
        textArea.setWrapText(true);
        textArea.setEditable(false);
        Button buttona = new Button("Get Selected Text");
        buttona.setOnAction(e -> {
            String selectedText = textArea.getSelectedText();
            textArea.selectRange(0,0);
            System.out.println("Selected text: " + selectedText);
        });

        VBox layout = new VBox(createBox, addBox, bindBox, printBox, textArea, buttona);
        layout.setSpacing(10);
        Scene scene = new Scene(layout, 400, 600);
        scene.getStylesheets().add(this.getClass().getResource("main.css").toExternalForm());
        window.setScene(scene);
        window.setTitle("JavaFX App");
        window.show();
    }

    private VBox getCreateBox() {
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
        VBox createBox = new VBox(titleInput, descInput, createButton);
        createBox.setSpacing(10);
        return createBox;
    }

    private VBox getAddBox() {
        TextField componentNameInput = new TextField();
        componentNameInput.setMaxWidth(200);

        ToggleGroup tg = new ToggleGroup();
        RadioButton r1 = new RadioButton("String");
        RadioButton r2 = new RadioButton("Number");
        RadioButton r3 = new RadioButton("Boolean");
        r1.setToggleGroup(tg);
        r2.setToggleGroup(tg);
        r3.setToggleGroup(tg);
        HBox typeSelector = new HBox(r1, r2, r3);

        TextField nameInput = new TextField();
        nameInput.setMaxWidth(200);
        TextField valueInput = new TextField();
        valueInput.setMaxWidth(200);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            RadioButton typeButton = (RadioButton) tg.getSelectedToggle();
            Class<?> type = Component.convertStringToType(typeButton.getText());
            Component component = components.get(componentNameInput.getText());
            if (type != null && component != null && !nameInput.getText().isEmpty() && !valueInput.getText().isEmpty()) {
                component.addProperty(nameInput.getText(), valueInput.getText(), type, true);
                nameInput.clear();
                valueInput.clear();
            }
        });
        VBox addBox = new VBox(componentNameInput, typeSelector, nameInput, valueInput, addButton);
        addBox.setSpacing(10);
        return addBox;
    }

    private static VBox getBindBox() {
        TextField componentInput1 = new TextField();
        componentInput1.setMaxWidth(200);
        TextField propNameInput1 = new TextField();
        propNameInput1.setMaxWidth(200);
        TextField componentInput2 = new TextField();
        componentInput2.setMaxWidth(200);
        TextField propNameInput2 = new TextField();
        propNameInput2.setMaxWidth(200);

        Button bindButton = new Button("Bind");
        bindButton.setOnAction(e -> {
            Component component1 = components.get(componentInput1.getText());
            Component component2 = components.get(componentInput2.getText());
            String changingPropertyName = propNameInput2.getText();
            component1.addListenerToProperty(propNameInput1.getText(), (v, oldValue, newValue) -> {
                component2.setPropertyValue(changingPropertyName, "" + (double) newValue * 5);
            });

            componentInput1.clear();
            propNameInput1.clear();
            componentInput2.clear();
            propNameInput2.clear();
        });
        VBox bindBox = new VBox(componentInput1, propNameInput1, componentInput2, propNameInput2, bindButton);
        bindBox.setSpacing(10);
        return bindBox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
