package ru.serobyan.json.token;

@lombok.Value
public class StringPrimitive implements Primitive {

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
