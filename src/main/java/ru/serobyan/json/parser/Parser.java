package ru.serobyan.json.parser;

import ru.serobyan.json.lexer.token.*;

import java.util.*;

public class Parser {

    private Iterator<Token> tokens;

    public static Parser of(Iterator<Token> tokens) {
        var parser = new Parser();
        parser.tokens = tokens;
        return parser;
    }

    public Object parse() {
        var firstToken = tokens.next();
        if (firstToken instanceof LeftSquare) {
            return parseArray();
        }
        if (firstToken instanceof LeftBrace) {
            return parseObject();
        }
        if (firstToken instanceof Primitive) {
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
            if (token instanceof Primitive) {
                result.add(((Primitive) token).getValue());
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
            if (token instanceof Primitive) {
                if (key == null) {
                    key = (String) ((Primitive) token).getValue();
                } else {
                    result.put(key, ((Primitive) token).getValue());
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
        return ((Primitive) token).getValue();
    }

}
