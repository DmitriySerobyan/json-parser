package ru.serobyan.json.lexer;

import lombok.SneakyThrows;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ru.serobyan.json.lexer.token.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LexerTest {

    @Test
    @SneakyThrows
    public void lex() {
        var json = LexerTest.class.getClassLoader().getResourceAsStream("test.json");
        var tokenIterator = Lexer.of(json).lex();
        var tokens = new ArrayList<Token>();
        while (tokenIterator.hasNext()) {
            tokens.add(tokenIterator.next());
        }
        var expected = List.of(
            Tokens.leftBrace(),
            Tokens.value("v1"), Tokens.colon(), Tokens.value(1), Tokens.comma(),
            Tokens.value("v2"), Tokens.colon(), Tokens.value("v"), Tokens.comma(),
            Tokens.value("v3"), Tokens.colon(), Tokens.value(2.0), Tokens.comma(),
            Tokens.value("v4"), Tokens.colon(), Tokens.value(true), Tokens.comma(),
            Tokens.value("v5"), Tokens.colon(), Tokens.leftSquare(), Tokens.value(2), Tokens.rightSquare(),
            Tokens.rightBrace()
        );
        MatcherAssert.assertThat(tokens, Matchers.contains(expected.toArray()));
    }

}