package com.example.tabletoprpgmaker;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.HashMap;
import java.util.Map;

public class Component {

    private static final ScriptEvaluator scriptEvaluator = new ScriptEvaluator();

    private final StringProperty title = new SimpleStringProperty(this, "firstname", "");
    private final StringProperty description = new SimpleStringProperty(this, "firstname", "");

    private final HashMap<String, ComplexStringProperty> stringProperties = new HashMap<>();
    private final HashMap<String, NumberProperty> numberProperties = new HashMap<>();
    private final HashMap<String, ComplexBooleanProperty> booleanProperties = new HashMap<>();
    private final HashMap<String, Class<?>> propertyTypes = new HashMap<>();

    /**
     * Constructs a Component with the specified title.
     *
     * @param title the title of the component. Acts as a key for the component
     */
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

    /**
     * Returns the ComponentProperty associated with the given name.
     *
     * @param name the name of the property
     * @return the ComponentProperty associated with the name, or null if not found
     */
    public ComponentProperty getProperty(String name) {
        Class<?> type = propertyTypes.get(name);
        if (type == null) {
            System.out.println("Error: No property with that name");
            return null;
        }
        HashMap<String, ?> map = getMapForType(type);
        if (map == null) return null;
        return (ComponentProperty) map.get(name);
    }

    /**
     * Adds a property to the component with the given name, value, type, and overwrite flag.
     *
     * @param name the name of the property
     * @param value the value of the property
     * @param type the data type of the property's value
     * @param overwrite whether to overwrite an existing property with the same name
     */
    public <T> void addProperty(String name, String value, Class<T> type, boolean overwrite) {
        if (type == Double.class)
            addPropertyHelper(name, value, type, overwrite, numberProperties);
        else if (type == Boolean.class)
            addPropertyHelper(name, value, type, overwrite, booleanProperties);
        else
            addPropertyHelper(name, value, type, overwrite, stringProperties);
    }

    /**
     * Helper method to add a property to the component with type-specific handling.
     *
     * @param name the name of the property
     * @param value the value of the property
     * @param type the data type of the property's value
     * @param overwrite whether to overwrite an existing property with the same name
     * @param properties the map of properties to update
     */
    private <S, U extends ComponentProperty> void addPropertyHelper(String name, String value, Class<S> type, boolean overwrite, HashMap<String, U> properties) {
        Object val = parseValue(value);
        ComponentProperty prop = properties.get(name);
        if (prop != null) {
            if (overwrite)
                prop.setVal(val);
            else
                System.out.println("Error: There is already a property with that name");
        }
        else {
            if (val instanceof Double)
                numberProperties.put(name, new NumberProperty(this, name, (Double) val));
            else if (val instanceof String)
                stringProperties.put(name, new ComplexStringProperty(this, name, (String) val));
            else if (val instanceof Boolean)
                booleanProperties.put(name, new ComplexBooleanProperty(this, name, (Boolean) val));
            propertyTypes.put(name, type);
        }
    }

    /**
     * Sets the value of a property by its name.
     *
     * @param name the name of the property
     * @param value the new value of the property
     */
    public void setPropertyValue(String name, String value) {
        Class<?> type = propertyTypes.get(name);
        if (type == null) return;
        addProperty(name, value, type, true);
    }

    /**
     * Adds listeners to properties that upon changing update the value of the dependent property
     *
     * @param scriptText the script to be executed that will update the dependent property
     * @param dependentPropComPair the property that will be updated by the script and the component it's from
     * @param changingPropComPairs the properties that trigger the script when they change and the components they're from
     */
    public static void addListenerToProperty(String scriptText, ComponentPropertyPair dependentPropComPair, ComponentPropertyPair... changingPropComPairs) {
        ComponentProperty[] properties = new ComponentProperty[changingPropComPairs.length];
        for (int i = 0; i < changingPropComPairs.length; i++) {
            properties[i] = changingPropComPairs[i].component().getProperty(changingPropComPairs[i].propertyName());
        }
        ComponentProperty dependentProperty = dependentPropComPair.component().getProperty(dependentPropComPair.propertyName());

        scriptEvaluator.evaluateScript(scriptText, dependentProperty, properties);
        for (ComponentProperty prop : properties) {
            ((ObservableValue<?>) prop).addListener((v, oldValue, newValue) -> {
                scriptEvaluator.evaluateScript(scriptText, dependentProperty, properties);
            });
        }
    }

    /**
     * Prints all properties of the component, including title, description, and all custom properties.
     */
    public void printAllProperties() {
        System.out.println("Title: " + getTitle());
        System.out.println("Description: " + getDescription());

        for (Map.Entry<String, ComplexStringProperty> prop : stringProperties.entrySet()) {
            System.out.println(prop.getKey() + ": " + prop.getValue().get() + " | String");
        }
        for (Map.Entry<String, NumberProperty> prop : numberProperties.entrySet()) {
            System.out.println(prop.getKey() + ": " + prop.getValue().get() + " | Number");
        }
        for (Map.Entry<String, ComplexBooleanProperty> prop : booleanProperties.entrySet()) {
            System.out.println(prop.getKey() + ": " + prop.getValue().get() + " | Boolean");
        }
    }

    /**
     * Returns the map of properties for the specified data type.
     *
     * @param type the data type associated with the desired map
     * @return the map of properties for the type, or null if the type is not recognized
     */
    private <T> HashMap<String, ?> getMapForType(Class<T> type) {
        if (type == String.class)
            return stringProperties;
        if (type == Double.class)
            return numberProperties;
        if (type == Boolean.class)
            return booleanProperties;
        return null;
    }

    /**
     * Converts a string representation of a type to the corresponding Class object.
     *
     * @param str the string representation of the type
     * @return the Class object for the type, or null if the type is not recognized
     */
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

    /**
     * Parses a string value into an appropriate type (Boolean, Double, or String).
     *
     * @param str the string value to parse
     * @return the parsed value as an Object
     */
    public static Object parseValue(String str) {
        if (str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(str);
        }
        else {
            try {
                return Double.parseDouble(str);
            } catch (NumberFormatException e) {
                return str;
            }
        }
    }
}
