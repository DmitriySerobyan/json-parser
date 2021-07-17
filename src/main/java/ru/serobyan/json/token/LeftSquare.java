package ru.serobyan.json.token;

import lombok.Value;

@Value
public class LeftSquare implements Token {

    @Override
    public String toString() {
        return "[";
    }

}
