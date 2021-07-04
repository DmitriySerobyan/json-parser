package ru.serobyan.json.lexer.token;

import lombok.Value;

@Value
public class DoubleValue implements Primitive {

    double value;

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "" + value;
    }

}
