package ru.serobyan.json.lexer.token;

import lombok.Value;

@Value
public class IntValue implements Token {

    int value;

    @Override
    public String toString() {
        return "" + value;
    }

}
