package ru.serobyan.json.lexer.token;

import lombok.Value;

@Value
public class RightSquare implements Token {

    @Override
    public String toString() {
        return "]";
    }

}
