package com.example.tabletoprpgmaker;

import javafx.beans.property.SimpleDoubleProperty;

public class NumberProperty extends SimpleDoubleProperty {

    boolean round = false;

    public NumberProperty(double v) {
        super(v);
    }

    @Override
    public void set(double v) {
        if (!round || v % 1 == 0) {
            super.set(v);
        }
    }
}
