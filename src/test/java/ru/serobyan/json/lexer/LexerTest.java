package ru.serobyan.json.lexer;

import org.junit.jupiter.api.Test;
import ru.serobyan.json.lexer.token.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LexerTest {

    @Test
    public void tokenize() throws IOException {
        var json = LexerTest.class.getClassLoader().getResourceAsStream("test.json");
        var tokenIterator = Lexer.of(json).tokenize();
        var tokens = new ArrayList<Token>();
        for (var t : tokenIterator) {
            tokens.add(t);
        }
        var expected = List.of(
            new LeftBrace(),
            new StringValue("v1"), new Colon(), new IntValue(1), new Comma(),
            new StringValue("v2"), new Colon(), new StringValue("v"), new Comma(),
            new StringValue("v3"), new Colon(), new DoubleValue(2.0), new Comma(),
            new StringValue("v4"), new Colon(), new BoolValue(true), new Comma(),
            new StringValue("v5"), new Colon(), new LeftSquare(), new IntValue(2), new RightSquare(),
            new RightBrace()
        );
        assertEquals(expected, tokens);
    }

}