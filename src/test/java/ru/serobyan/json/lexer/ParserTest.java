package ru.serobyan.json.lexer;

import lombok.SneakyThrows;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ru.serobyan.json.lexer.token.Token;
import ru.serobyan.json.lexer.token.Tokens;
import ru.serobyan.json.parser.Parser;

import java.util.List;
import java.util.Map;

class ParserTest {

    @Test
    @SneakyThrows
    public void parseObject() {
        var tokens = List.of(
            Tokens.leftBrace(),
            Tokens.value("v1"), Tokens.colon(), Tokens.value(1), Tokens.comma(),
            Tokens.value("v2"), Tokens.colon(), Tokens.value("test"),
            Tokens.rightBrace()
        );
        var expected = Map.of(
            "v1", 1,
            "v2", "test"
        );
        var parser = Parser.of(tokens.listIterator());
        var result = parser.parse();
        MatcherAssert.assertThat(result, Matchers.equalTo(expected));
    }

    @Test
    @SneakyThrows
    public void parseArray() {
        var tokens = List.of(
            Tokens.leftSquare(),
            Tokens.value(1), Tokens.comma(),
            Tokens.value(2),
            Tokens.rightSquare()
        );
        var expected = List.of(
            1,
            2
        );
        var parser = Parser.of(tokens.listIterator());
        var result = parser.parse();
        MatcherAssert.assertThat(result, Matchers.equalTo(expected));
    }

    @Test
    @SneakyThrows
    public void parsePrimitive() {
        var tokens = List.<Token>of(
            Tokens.value(1)
        );
        var expected = 1;
        var parser = Parser.of(tokens.listIterator());
        var result = parser.parse();
        MatcherAssert.assertThat(result, Matchers.equalTo(expected));
    }

}