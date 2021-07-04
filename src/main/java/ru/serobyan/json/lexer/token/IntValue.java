package ru.serobyan.json.lexer.token;

import lombok.Value;

@Value
public class IntValue implements Primitive {

    int value;

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "" + value;
    }

}
