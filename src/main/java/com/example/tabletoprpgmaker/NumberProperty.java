package com.example.tabletoprpgmaker;

import javafx.beans.property.SimpleDoubleProperty;

public class NumberProperty extends SimpleDoubleProperty implements ComponentProperty{

    boolean round = false;

    /**
     * Constructs a NumberProperty with the specified bean, name, and initial value.
     *
     * @param bean the bean that holds this property
     * @param name the name of this property
     * @param v the initial double value of this property
     */
    public NumberProperty(Object bean, String name, double v) {
        super(bean, name, v);
    }

    /**
     * Sets the value of the property if rounding is disabled or if rounding is enabled and the value is an integer.
     *
     * @param v the value to be set
     */
    @Override
    public void set(double v) {
        if (!round || v % 1 == 0) {
            super.set(v);
        }
    }

    /**
     * Returns the double value of the property as a string.
     *
     * @return the double value as a string
     */
    @Override
    public String getStringVal() {
        return "" + super.get();
    }

    /**
     * Sets the value of the property if the provided object is of type Double.
     * Prints an error message if the object is not a number.
     *
     * @param obj the value to be set
     */
    @Override
    public void setVal(Object obj) {
        if (obj instanceof Double) {
            set((Double) obj);
        } else {
            System.out.println("Value is not a number");
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
