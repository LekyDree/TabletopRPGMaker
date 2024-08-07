package com.example.tabletoprpgmaker;

import javafx.beans.property.SimpleBooleanProperty;

public class ComplexBooleanProperty extends SimpleBooleanProperty implements ComponentProperty {

    /**
     * Constructs a ComplexBooleanProperty with the specified bean, name, and initial value.
     *
     * @param bean the bean that holds this property
     * @param name the name of this property
     * @param b the initial boolean value of this property
     */
    public ComplexBooleanProperty(Object bean, String name, boolean b) {
        super(bean, name, b);
    }

    /**
     * Returns the boolean value of the property as a string.
     *
     * @return the boolean value as a string
     */
    @Override
    public String getStringVal() {
        return "" + super.get();
    }

    /**
     * Sets the value of the property if the provided object is of type Boolean.
     * Prints an error message if the object is not a boolean.
     *
     * @param obj the value to be set
     */
    @Override
    public void setVal(Object obj) {
        if (obj instanceof Boolean) {
            super.set((Boolean) obj);
        } else {
            System.out.println("Value is not a boolean");
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
