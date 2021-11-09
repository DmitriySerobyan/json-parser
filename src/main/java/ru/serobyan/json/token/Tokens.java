package ru.serobyan.json.token;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Tokens {

    public StringPrimitive value(String value) {
        return new StringPrimitive(value);
    }

    public IntPrimitive value(int value) {
        return new IntPrimitive(value);
    }

    public DoublePrimitive value(double value) {
        return new DoublePrimitive(value);
    }

    public BoolPrimitive value(boolean value) {
        return new BoolPrimitive(value);
    }

    public RightSquare rightSquare() {
        return new RightSquare();
    }

    public RightBrace rightBrace() {
        return new RightBrace();
    }

    public LeftSquare leftSquare() {
        return new LeftSquare();
    }

    public LeftBrace leftBrace() {
        return new LeftBrace();
    }

    public Comma comma() {
        return new Comma();
    }

    public Colon colon() {
        return new Colon();
    }

    public EOF EOF() {
        return new EOF();
    }

}
