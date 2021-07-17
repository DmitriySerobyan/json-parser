package ru.serobyan.json.parser;

import ru.serobyan.json.token.*;

import java.util.*;

public class Parser {

    private final Iterator<Token> tokens;

    private Parser(Iterator<Token> tokens) {
        this.tokens = tokens;
    }

    public static Parser of(Iterator<Token> tokens) {
        return new Parser(tokens);
    }

    public Object parse() {
        var firstToken = tokens.next();
        if (firstToken instanceof LeftSquare) {
            return parseArray();
        }
        if (firstToken instanceof LeftBrace) {
            return parseObject();
        }
        if (firstToken instanceof Value) {
            return parsePrimitive(firstToken);
        }
        throw new RuntimeException();
    }

    List<Object> parseArray() {
        var result = new ArrayList<>();
        while (tokens.hasNext()) {
            var token = tokens.next();
            if (token instanceof RightSquare) {
                return result;
            }
            if (token instanceof Value) {
                result.add(((Value) token).getValue());
            }
            if (token instanceof LeftBrace) {
                result.add(parseObject());
            }
            if (token instanceof LeftBrace) {
                return parseArray();
            }
        }
        throw new RuntimeException();
    }

    Map<String, Object> parseObject() {
        var result = new HashMap<String, Object>();
        String key = null;
        while (tokens.hasNext()) {
            var token = tokens.next();
            if (token instanceof RightBrace) {
                return result;
            }
            if (token instanceof Value) {
                if (key == null) {
                    key = (String) ((Value) token).getValue();
                } else {
                    result.put(key, ((Value) token).getValue());
                    key = null;
                }
            }
            if (token instanceof LeftBrace) {
                result.put(key, parseObject());
            }
            if (token instanceof LeftSquare) {
                result.put(key, parseArray());
            }
        }
        throw new RuntimeException();
    }

    Object parsePrimitive(Token token) {
        return ((Value) token).getValue();
    }

}
