package ru.serobyan.json.token;

import lombok.Value;

@Value
public class LeftBrace implements Token {

    @Override
    public String toString() {
        return "{";
    }

}
