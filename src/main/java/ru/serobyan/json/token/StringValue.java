package ru.serobyan.json.token;

@lombok.Value
public class StringValue implements Value {

    String value;

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }

}
