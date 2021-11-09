package ru.serobyan.json.token;

@lombok.Value
public class BoolPrimitive implements Primitive {

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
