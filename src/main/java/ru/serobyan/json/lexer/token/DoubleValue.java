package ru.serobyan.json.lexer.token;

import lombok.Value;

@Value
public class DoubleValue implements Token {

    double value;

    @Override
    public String toString() {
        return "" + value;
    }

}
