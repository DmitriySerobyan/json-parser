package ru.serobyan.json.parser;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.serobyan.json.lexer.Lexer;
import ru.serobyan.json.token.*;

import java.io.InputStream;
import java.io.Reader;
import java.util.*;

public class Parser {

    private final Lexer lexer;

    public Parser(String str) {
        this.lexer = new Lexer(str);
    }

    public Parser(InputStream input) {
        this.lexer = new Lexer(input);
    }

    public Parser(Reader reader) {
        this.lexer = new Lexer(reader);
    }

    public Object parse() {
        var result = parseValue();
        assertNextTokenIs(EOF.class);
        return result;
    }

    private Object parseValue() {
        Object result = null;
        assertNextTokenIs(Primitive.class, LeftSquare.class, LeftBrace.class);
        var firstToken = lexer.peek();
        if (firstToken instanceof Primitive) {
            result = parsePrimitive();
        }
        if (firstToken instanceof LeftSquare) {
            result = parseArray();
        }
        if (firstToken instanceof LeftBrace) {
            result = parseObject();
        }
        return result;
    }

    private Object parsePrimitive() {
        assertNextTokenIs(Primitive.class);
        var token = lexer.next();
        return ((Primitive) token).getValue();
    }

    private List<Object> parseArray() {
        var result = new ArrayList<>();
        assertNextTokenIs(LeftSquare.class);
        lexer.next();
        while (lexer.hasNext()) {
            var nextToken = lexer.peek();
            if (nextToken instanceof RightSquare) {
                lexer.next();
                return result;
            }
            var value = parseValue();
            result.add(value);
            if (!lexer.hasNext()) {
                throw new RuntimeException(String.valueOf(lexer.getPosition()));
            }
            nextToken = lexer.peek();
            if (nextToken instanceof Comma) {
                lexer.next();
                assertNextTokenIs(Primitive.class, LeftSquare.class, LeftBrace.class);
                continue;
            }
            if (nextToken instanceof RightSquare) {
                lexer.next();
                return result;
            }
            throw new RuntimeException(String.valueOf(lexer.getPosition()));
        }
        throw new RuntimeException(String.valueOf(lexer.getPosition()));
    }

    private Map<String, Object> parseObject() {
        var result = new HashMap<String, Object>();
        assertNextTokenIs(LeftBrace.class);
        lexer.next();
        while (lexer.hasNext()) {
            var nextToken = lexer.peek();
            if (nextToken instanceof RightBrace) {
                lexer.next();
                return result;
            }
            var pair = parsePair();
            result.put(pair.getKey(), pair.getValue());
            if (!lexer.hasNext()) {
                throw new RuntimeException(String.valueOf(lexer.getPosition()));
            }
            nextToken = lexer.peek();
            if (nextToken instanceof Comma) {
                lexer.next();
                assertNextTokenIs(Primitive.class);
                continue;
            }
            if (nextToken instanceof RightBrace) {
                lexer.next();
                return result;
            }
            throw new RuntimeException(String.valueOf(lexer.getPosition()));
        }
        throw new RuntimeException(String.valueOf(lexer.getPosition()));
    }

    private Pair parsePair() {
        var key = (String) parsePrimitive();
        assertNextTokenIs(Colon.class);
        lexer.next();
        var value = parseValue();
        return new Pair(key, value);
    }

    private void assertNextTokenIs(Class<?>... assertTokenTypes) {
        if (!lexer.hasNext()) {
            throw new RuntimeException(String.valueOf(lexer.getPosition()));
        }
        var nextTokenType = lexer.peek().getClass();
        for (var assertTokenType : assertTokenTypes) {
            if (assertTokenType.isAssignableFrom(nextTokenType)) {
                return;
            }
        }
        throw new RuntimeException(String.valueOf(lexer.getPosition()));
    }

    @Data
    @AllArgsConstructor
    private static class Pair {
        private String key;
        private Object value;
    }

}
