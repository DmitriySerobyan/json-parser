package ru.serobyan.json.token;

import lombok.Value;

@Value
public class RightBrace implements Token {

    @Override
    public String toString() {
        return "}";
    }

}
