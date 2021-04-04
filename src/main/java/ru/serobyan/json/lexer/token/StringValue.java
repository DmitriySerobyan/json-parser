package ru.serobyan.json.lexer.token;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
public class StringValue implements Token {

    String value;

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }

}
