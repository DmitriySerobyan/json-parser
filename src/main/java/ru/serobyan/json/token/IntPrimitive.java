package ru.serobyan.json.token;

@lombok.Value
public class IntPrimitive implements Primitive {

    int value;

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "" + value;
    }

}
