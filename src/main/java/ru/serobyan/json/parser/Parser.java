package ru.serobyan.json.parser;

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
        Object result = null;
        assertNextTokenIs(Value.class, LeftSquare.class, LeftBrace.class);
        var firstToken = lexer.peek();
        if (firstToken instanceof Value) {
            result = parsePrimitive();
        }
        if (firstToken instanceof LeftSquare) {
            result = parseArray();
        }
        if (firstToken instanceof LeftBrace) {
            result = parseObject();
        }
        assertNextTokenIs(EOF.class);
        return result;
    }

    Object parsePrimitive() {
        assertNextTokenIs(Value.class);
        var token = lexer.next();
        return ((Value) token).getValue();
    }

    List<Object> parseArray() {
        var result = new ArrayList<>();
        assertNextTokenIs(LeftSquare.class);
        lexer.next();
        assertNextTokenIs(RightSquare.class, Value.class, LeftSquare.class, LeftBrace.class);
        while (lexer.hasNext()) {
            assertNextTokenIs(RightSquare.class, Comma.class, Value.class, LeftSquare.class, LeftBrace.class);
            var nextToken = lexer.peek();
            if (nextToken instanceof Comma) {
                lexer.next();
                continue;
            }
            if (nextToken instanceof RightSquare) {
                lexer.next();
                return result;
            }
            if (nextToken instanceof Value) {
                result.add(parsePrimitive());
            }
            if (nextToken instanceof LeftSquare) {
                result.add(parseArray());
            }
            if (nextToken instanceof LeftBrace) {
                result.add(parseObject());
            }
            assertNextTokenIs(Comma.class, RightSquare.class);
        }
        throw new RuntimeException(String.valueOf(lexer.getPosition()));
    }

    Map<String, Object> parseObject() {
        var result = new HashMap<String, Object>();
        assertNextTokenIs(LeftBrace.class);
        lexer.next();
        String key = null;
        assertNextTokenIs(Value.class, RightBrace.class);
        while (lexer.hasNext()) {
            var nextToken = lexer.peek();
            if (nextToken instanceof Comma) {
                lexer.next();
                continue;
            }
            if (nextToken instanceof RightBrace) {
                lexer.next();
                return result;
            }
            if (key == null) {
                key = (String) parsePrimitive();
                assertNextTokenIs(Colon.class);
                lexer.next();
                continue;
            } else {
                assertNextTokenIs(Value.class, LeftSquare.class, LeftBrace.class);
                nextToken = lexer.peek();
                if (nextToken instanceof Value) {
                    result.put(key, parsePrimitive());
                }
                if (nextToken instanceof LeftSquare) {
                    result.put(key, parseArray());
                }
                if (nextToken instanceof LeftBrace) {
                    result.put(key, parseObject());
                }
                key = null;
                assertNextTokenIs(Comma.class, RightBrace.class);
            }
        }
        throw new RuntimeException(String.valueOf(lexer.getPosition()));
    }

    void assertNextTokenIs(Class<?>... assertTokenTypes) {
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

}
