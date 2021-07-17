package ru.serobyan.json.token;

import lombok.Value;

@Value
public class EOF implements Token {

    @Override
    public String toString() {
        return "";
    }

}
