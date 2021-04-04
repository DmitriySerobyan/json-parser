package ru.serobyan.json.lexer.token;

import lombok.Value;

@Value
public class RightBrace implements Token {

    @Override
    public String toString() {
        return "}";
    }

}
