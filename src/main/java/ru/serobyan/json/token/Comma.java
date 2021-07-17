package ru.serobyan.json.token;

import lombok.Value;

@Value
public class Comma implements Token {

    @Override
    public String toString() {
        return ",";
    }

}
