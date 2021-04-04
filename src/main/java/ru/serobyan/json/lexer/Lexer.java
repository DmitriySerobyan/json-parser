package ru.serobyan.json.lexer;

import lombok.SneakyThrows;
import ru.serobyan.json.lexer.token.Token;
import ru.serobyan.json.lexer.token.Tokens;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Character.*;

public class Lexer {

    private InputStreamReader reader;
    private Deque<String> nexts = new LinkedList<>();

    public static Lexer of(InputStream input) {
        var lexer = new Lexer();
        lexer.reader = new InputStreamReader(input, StandardCharsets.UTF_8);
        return lexer;
    }

    public Iterator<Token> lex() {
        return new Iterator<>() {

            @Override
            @SneakyThrows
            public boolean hasNext() {
                if (!nexts.isEmpty()) {
                    return true;
                }
                int intCh;
                StringBuilder stringBuilder = null;
                while ((intCh = reader.read()) != -1) {
                    var ch = (char) intCh;
                    if (ch == '{' || ch == '}' || ch == '[' || ch == ']' || ch == ':' || ch == ',') {
                        if (stringBuilder != null) {
                            nexts.add(stringBuilder.toString());
                        }
                        nexts.add(String.valueOf(ch));
                        return !nexts.isEmpty();
                    }
                    if (ch == '"') {
                        if (stringBuilder == null) {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(ch);
                        } else {
                            stringBuilder.append(ch);
                            nexts.add(stringBuilder.toString());
                            return !nexts.isEmpty();
                        }
                    }
                    if (ch == '.' || isLetter(ch) || isDigit(ch)) {
                        if (stringBuilder == null) {
                            stringBuilder = new StringBuilder();
                        }
                        stringBuilder.append(ch);
                    }
                }
                return false;
            }

            @Override
            public Token next() {
                var rawToken = nexts.removeFirst();
                if (rawToken.startsWith("\"")) {
                    var string = rawToken.substring(1, rawToken.length() - 1);
                    return Tokens.value(string);
                }
                if (rawToken.equals("true") || rawToken.equals("false")) {
                    return Tokens.value(Boolean.parseBoolean(rawToken));
                }
                if (rawToken.equals("{")) {
                    return Tokens.leftBrace();
                }
                if (rawToken.equals("}")) {
                    return Tokens.rightBrace();
                }
                if (rawToken.equals("[")) {
                    return Tokens.leftSquare();
                }
                if (rawToken.equals("]")) {
                    return Tokens.rightSquare();
                }
                if (rawToken.equals(":")) {
                    return Tokens.colon();
                }
                if (rawToken.equals(",")) {
                    return Tokens.comma();
                }
                if (rawToken.contains(".")) {
                    return Tokens.value(Double.parseDouble(rawToken));
                }
                return Tokens.value(Integer.parseInt(rawToken));
            }
        };
    }

}
