package com.example.tabletoprpgmaker;

public interface ComponentProperty {

    /**
     * Returns the value of the property as a string.
     *
     * @return the value of the property as a string
     */
    public String getStringVal();

    /**
     * Sets the value of the property from the provided object.
     * The object should be of a type that is appropriate for this property.
     *
     * @param obj the value to be set
     */
    public void setVal(Object obj);

    /**
     * Returns the name of the property.
     *
     * @return the name of the property
     */
    public String getName();
}
