package ru.serobyan.json.token;

@lombok.Value
public class BoolValue implements Value {

    boolean value;

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "" + value;
    }

}
