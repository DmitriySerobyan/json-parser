package ru.serobyan.json.lexer.token;

import lombok.Value;

@Value
public class BoolValue implements Primitive {

    boolean value;

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "" + value;
    }

}
