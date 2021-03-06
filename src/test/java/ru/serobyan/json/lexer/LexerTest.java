package ru.serobyan.json.lexer;

import lombok.SneakyThrows;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ru.serobyan.json.token.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LexerTest {

    @Test
    @SneakyThrows
    public void lex() {
        var json = LexerTest.class.getClassLoader().getResourceAsStream("test.json");
        var lexer = new Lexer(json);
        var tokens = new ArrayList<Token>();
        while (lexer.hasNext()) {
            tokens.add(lexer.next());
        }
        var expected = List.of(
            Tokens.leftBrace(),
            Tokens.value("v1"), Tokens.colon(), Tokens.value(100), Tokens.comma(),
            Tokens.value("v2"), Tokens.colon(), Tokens.value("test"), Tokens.comma(),
            Tokens.value("v3"), Tokens.colon(), Tokens.value(2.0), Tokens.comma(),
            Tokens.value("v4"), Tokens.colon(), Tokens.value(true), Tokens.comma(),
            Tokens.value("v5"), Tokens.colon(), Tokens.leftSquare(), Tokens.value(2), Tokens.rightSquare(),
            Tokens.rightBrace(),
            Tokens.EOF()
        );
        MatcherAssert.assertThat(tokens, Matchers.contains(expected.toArray()));
    }

}