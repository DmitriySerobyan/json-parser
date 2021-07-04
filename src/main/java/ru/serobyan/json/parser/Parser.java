package ru.serobyan.json.parser;

import ru.serobyan.json.lexer.token.*;

import java.util.*;

public class Parser {

    private List<Token> tokens;
    private int position = 0;

    public static Parser of(Iterator<Token> tokenIterator) {
        var tokens = new ArrayList<Token>();
        while (tokenIterator.hasNext()) {
            tokens.add(tokenIterator.next());
        }
        return of(tokens);
    }

    public static Parser of(List<Token> tokens) {
        var parser = new Parser();
        parser.tokens = tokens;
        return parser;
    }

    public Object parse() {
        var firstToken = tokens.get(0);
        if (firstToken instanceof LeftSquare) {
            return parseArray();
        }
        if (firstToken instanceof LeftBrace) {
            return parseObject();
        }
        if (firstToken instanceof Primitive) {
            return parsePrimitive();
        }
        throw new RuntimeException();
    }

    List<Object> parseArray() {
        position++;
        var result = new ArrayList<>();
        while (position < tokens.size()) {
            var token = tokens.get(position);
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
            position++;
        }
        throw new RuntimeException();
    }

    Map<String, Object> parseObject() {
        position++;
        var result = new HashMap<String, Object>();
        String key = null;
        while (position < tokens.size()) {
            var token = tokens.get(position);
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
            position++;
        }
        throw new RuntimeException();
    }

    Object parsePrimitive() {
        var token = tokens.get(position);
        return ((Primitive) token).getValue();
    }

}
