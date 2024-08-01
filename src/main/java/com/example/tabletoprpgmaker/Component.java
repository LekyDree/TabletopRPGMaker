package com.example.tabletoprpgmaker;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Component {

    private final StringProperty title = new SimpleStringProperty(this, "firstname", "");
    private final StringProperty description = new SimpleStringProperty(this, "firstname", "");

    private final HashMap<String, SimpleObjectProperty<Object>> properties = new HashMap<>();
    
    public Component(String title) {
        setTitle(title);
    }


    public StringProperty titleProperty() {
        return title;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }


    public StringProperty descriptionProperty() {
        return description;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    /*public HashMap<String, SimpleObjectProperty<String>> getProperties() {
        return properties;
    }*/

    public void addProperty(String name, Object value) {
        SimpleObjectProperty<Object> property = new SimpleObjectProperty<>(value);
        properties.put(name, property);
    }

    public void setPropertyValue(String name, String value) {
        SimpleObjectProperty<Object> property = properties.get(name);
        if (property != null) {
            property.set(value);
        }
    }

    public void addListenerToProperty(String name, ChangeListener<Object> listener) {
        SimpleObjectProperty<Object> property = properties.get(name);
        if (property != null) {
            property.addListener(listener);
        }
    }

    public void printProperties() {
        System.out.println("Title: " + getTitle());
        System.out.println("Description: " + getDescription());

        for (Map.Entry<String, SimpleObjectProperty<Object>> prop : properties.entrySet()) {
            System.out.println(prop.getKey() + ": " + prop.getValue().get());
        }
    }
}
