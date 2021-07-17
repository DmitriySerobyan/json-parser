package ru.serobyan.json.token;

@lombok.Value
public class DoubleValue implements Value {

    double value;

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "" + value;
    }

}
