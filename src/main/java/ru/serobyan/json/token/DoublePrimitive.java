package ru.serobyan.json.token;

@lombok.Value
public class DoublePrimitive implements Primitive {

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
