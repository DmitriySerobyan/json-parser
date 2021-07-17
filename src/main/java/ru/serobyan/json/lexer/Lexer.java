package ru.serobyan.json.lexer;

import lombok.SneakyThrows;
import ru.serobyan.json.token.Token;
import ru.serobyan.json.token.Tokens;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import static java.lang.Character.*;;

public class Lexer {

    public Iterator<Token> lex(InputStream input) {
        return lex(new InputStreamReader(input, StandardCharsets.UTF_8));
    }

    public Iterator<Token> lex(InputStreamReader reader) {
        return new Iterator<>() {

            private final Deque<Token> nextTokens = new LinkedList<>();
            private int position = -1;
            private boolean isDone = false;

            @Override
            @SneakyThrows
            public boolean hasNext() {
                if (!nextTokens.isEmpty()) {
                    return true;
                }
                int intCh;
                StringBuilder stringBuilder = null;
                boolean isStringReading = false;
                while (!isDone) {
                    intCh = reader.read();
                    if (intCh == -1) {
                        isDone = true;
                        if (isStringReading) {
                            throw new RuntimeException(String.valueOf(position));
                        }
                        nextTokens.add(Tokens.EOF());
                        return !nextTokens.isEmpty();
                    }
                    position++;
                    var ch = (char) intCh;
                    if (isStringReading) {
                        stringBuilder.append(ch);
                        if (ch == '\n') {
                            throw new RuntimeException(String.valueOf(position));
                        }
                        if (ch == '"') {
                            nextTokens.add(parseToken(stringBuilder.toString()));
                            isStringReading = false;
                            stringBuilder = null;
                            return !nextTokens.isEmpty();
                        }
                        continue;
                    }
                    if (ch == '"') {
                        isStringReading = true;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(ch);
                        continue;
                    }
                    if (ch == '-' || ch == '+' || ch == '.' || isLetter(ch) || isDigit(ch)) {
                        if (stringBuilder == null) {
                            stringBuilder = new StringBuilder();
                        }
                        stringBuilder.append(ch);
                        continue;
                    }
                    if (ch == '{') {
                        if (stringBuilder != null) {
                            throw new RuntimeException(String.valueOf(position));
                        }
                        nextTokens.add(Tokens.leftBrace());
                        return !nextTokens.isEmpty();
                    }
                    if (ch == '[') {
                        if (stringBuilder != null) {
                            throw new RuntimeException(String.valueOf(position));
                        }
                        nextTokens.add(Tokens.leftSquare());
                        return !nextTokens.isEmpty();
                    }
                    if (ch == '}') {
                        if (stringBuilder != null) {
                            nextTokens.add(parseToken(stringBuilder.toString()));
                            stringBuilder = null;
                        }
                        nextTokens.add(Tokens.rightBrace());
                        return !nextTokens.isEmpty();
                    }
                    if (ch == ']') {
                        if (stringBuilder != null) {
                            nextTokens.add(parseToken(stringBuilder.toString()));
                            stringBuilder = null;
                        }
                        nextTokens.add(Tokens.rightSquare());
                        return !nextTokens.isEmpty();
                    }
                    if (ch == ':') {
                        if (stringBuilder != null) {
                            nextTokens.add(parseToken(stringBuilder.toString()));
                            stringBuilder = null;
                        }
                        nextTokens.add(Tokens.colon());
                        return !nextTokens.isEmpty();
                    }
                    if (ch == ',') {
                        if (stringBuilder != null) {
                            nextTokens.add(parseToken(stringBuilder.toString()));
                            stringBuilder = null;
                        }
                        nextTokens.add(Tokens.comma());
                        return !nextTokens.isEmpty();
                    }
                    if (isSpace(ch) && stringBuilder != null) {
                        nextTokens.add(parseToken(stringBuilder.toString()));
                        stringBuilder = null;
                        return !nextTokens.isEmpty();
                    }
                }
                return !nextTokens.isEmpty();
            }

            @Override
            public Token next() {
                return nextTokens.removeFirst();
            }

            private boolean isSpace(char ch) {
                return ch == ' ' || ch == '\r' || ch == '\n' || ch == '\t';
            }

            private Token parseToken(String rawToken) {
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
