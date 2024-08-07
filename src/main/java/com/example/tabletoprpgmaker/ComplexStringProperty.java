package com.example.tabletoprpgmaker;

import javafx.beans.property.SimpleStringProperty;

public class ComplexStringProperty extends SimpleStringProperty implements ComponentProperty{

    /**
     * Constructs a ComplexStringProperty with the specified bean, name, and initial string value.
     *
     * @param bean the bean that holds this property
     * @param name the name of this property
     * @param s the initial string value of this property
     */
    public ComplexStringProperty(Object bean, String name, String s) {
        super(bean, name, s);
    }

    /**
     * Returns the string value of the property.
     *
     * @return the string value of the property
     */
    @Override
    public String getStringVal() {
        return super.get();
    }

    /**
     * Sets the value of the property if the provided object is of type String.
     * Prints an error message if the object is not a string.
     *
     * @param obj the value to be set
     */
    @Override
    public void setVal(Object obj) {
        if (obj instanceof String) {
            super.set((String) obj);
        } else {
            System.out.println("Value is not a string");
        }
    }

    /**
     * Returns the name of the property.
     *
     * @return the name of the property
     */
    @Override
    public String getName() {
        return super.getName();
    }
}
