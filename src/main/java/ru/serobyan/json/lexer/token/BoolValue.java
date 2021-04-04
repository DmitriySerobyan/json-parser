package ru.serobyan.json.lexer.token;

import lombok.Value;

@Value
public class BoolValue implements Token {

    boolean value;

    @Override
    public String toString() {
        return "" + value;
    }

}
