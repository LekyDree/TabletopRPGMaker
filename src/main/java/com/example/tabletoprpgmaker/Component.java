package com.example.tabletoprpgmaker;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.HashMap;
import java.util.Map;

public class Component {

    private final StringProperty title = new SimpleStringProperty(this, "firstname", "");
    private final StringProperty description = new SimpleStringProperty(this, "firstname", "");

    private final HashMap<String, ComplexStringProperty> stringProperties = new HashMap<>();
    private final HashMap<String, NumberProperty> numberProperties = new HashMap<>();
    private final HashMap<String, ComplexBooleanProperty> booleanProperties = new HashMap<>();
    private final HashMap<String, Class<?>> propertyTypes = new HashMap<>();

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

    public double getNumberPropertyValue(String name) {
        return numberProperties.get(name).get();
    }

    public boolean getBoolPropertyValue(String name) {
        return booleanProperties.get(name).get();
    }

    public String getStringPropertyValue(String name) {
        return stringProperties.get(name).get();
    }

    public <T> void addProperty(String name, String value, Class<T> type, boolean overwrite) {
        HashMap<String, ?> propertyMap = getMapForType(type);
        if (type == Double.class) {
            try {
                addDoubleProperty(name, value, overwrite);
            } catch (Exception e) {
                System.out.println("Not a valid decimal number");
            }
        }
        else if (type == Boolean.class) {
            try {
                addBoolProperty(name, value, overwrite);
            } catch (Exception e) {
                System.out.println("Not a valid boolean");
            }
        }
        else {
            addStringProperty(name, value, overwrite);
        }
    }

    private void addDoubleProperty(String name, String value, boolean overwrite) throws NumberFormatException {
        double val = Double.parseDouble(value);
        DoubleProperty prop = numberProperties.get(name);
        if (prop != null) {
            if (overwrite)
                prop.set(val);
            else
                System.out.println("Error: There is already a property with that name");
        }
        else {
            numberProperties.put(name, new NumberProperty(val));
            propertyTypes.put(name, Double.class);
        }
    }

    private void addBoolProperty(String name, String value, boolean overwrite) throws Exception {
        boolean val;
        if (value.equalsIgnoreCase("true"))
            val = true;
        else if (value.equalsIgnoreCase("false"))
            val = false;
        else
            throw new Exception();
        BooleanProperty prop = booleanProperties.get(name);
        if (prop != null) {
            if (overwrite)
                prop.set(val);
            else
                System.out.println("Error: There is already a property with that name");
        }
        else {
            booleanProperties.put(name, new ComplexBooleanProperty(val));
            propertyTypes.put(name, Boolean.class);
        }
    }

    private void addStringProperty(String name, String value, boolean overwrite) {
        StringProperty prop = stringProperties.get(name);
        if (prop != null) {
            if (overwrite)
                prop.set(value);
            else
                System.out.println("Error: There is already a property with that name");
        }
        else {
            stringProperties.put(name, new ComplexStringProperty(value));
            propertyTypes.put(name, String.class);
        }
    }

    public void setPropertyValue(String name, String value) {
        Class<?> type = propertyTypes.get(name);
        if (type == null) return;
        addProperty(name, value, type, true);
    }

    public void addListenerToProperty(String name, ChangeListener<Object> listener) {
        Class<?> type = propertyTypes.get(name);
        if (type == null) {
            System.out.println("Error: No property with that name");
            return;
        }
        HashMap<String, ?> map = getMapForType(type);
        if (map != null) {
            ObservableValue<?> property = (ObservableValue<?>) map.get(name);
            if (property != null) {
                property.addListener(listener);
            }
        }
    }

    public void printAllProperties() {
        System.out.println("Title: " + getTitle());
        System.out.println("Description: " + getDescription());

        System.out.println("String Properties:");
        for (Map.Entry<String, ComplexStringProperty> prop : stringProperties.entrySet()) {
            System.out.println(prop.getKey() + ": " + prop.getValue().get());
        }
        System.out.println("Number Properties:");
        for (Map.Entry<String, NumberProperty> prop : numberProperties.entrySet()) {
            System.out.println(prop.getKey() + ": " + prop.getValue().get());
        }
        System.out.println("Boolean Properties:");
        for (Map.Entry<String, ComplexBooleanProperty> prop : booleanProperties.entrySet()) {
            System.out.println(prop.getKey() + ": " + prop.getValue().get());
        }
    }

    private <T> HashMap<String, ?> getMapForType(Class<T> type) {
        if (type == String.class)
            return stringProperties;
        if (type == Double.class)
            return numberProperties;
        if (type == Boolean.class)
            return booleanProperties;
        return null;
    }

    public static Class<?> convertStringToType(String str) {
        if (str.equalsIgnoreCase("string"))
            return String.class;
        else if (str.equalsIgnoreCase("number"))
            return Double.class;
        else if (str.equalsIgnoreCase("boolean"))
            return Boolean.class;
        System.out.println("Not a valid type");
        return null;
    }
}
