package ru.serobyan.json.parser;

import ru.serobyan.json.lexer.Lexer;
import ru.serobyan.json.token.*;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (!lexer.hasNext()) {
            throw new RuntimeException();
        }
        var firstToken = lexer.next();
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
        while (lexer.hasNext()) {
            var token = lexer.next();
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
        while (lexer.hasNext()) {
            var token = lexer.next();
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
