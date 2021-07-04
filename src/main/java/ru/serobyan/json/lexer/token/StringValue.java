package ru.serobyan.json.lexer.token;

import lombok.Value;

@Value
public class StringValue implements Primitive {

    String value;

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }

}
