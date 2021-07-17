package ru.serobyan.json.token;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Tokens {

    public StringValue value(String value) {
        return new StringValue(value);
    }

    public IntValue value(int value) {
        return new IntValue(value);
    }

    public DoubleValue value(double value) {
        return new DoubleValue(value);
    }

    public BoolValue value(boolean value) {
        return new BoolValue(value);
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

}
