package ru.serobyan.json.lexer;

import ru.serobyan.json.lexer.token.Token;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Lexer {

    private InputStream input;

    public static Lexer of(InputStream input) {
        var lexer = new Lexer();
        lexer.input = input;
        return lexer;
    }

    public Iterable<Token> tokenize() throws IOException {
        var reader = new InputStreamReader(input, StandardCharsets.UTF_8);
        int intCh;
        while ((intCh = reader.read()) != -1) {
            var ch = (char) intCh;
            // TODO
        }
        return List.of();
    }


}
