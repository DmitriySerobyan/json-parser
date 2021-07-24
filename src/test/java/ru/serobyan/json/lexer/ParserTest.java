package ru.serobyan.json.lexer;

import lombok.SneakyThrows;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ru.serobyan.json.parser.Parser;

import java.util.List;
import java.util.Map;

class ParserTest {

    @Test
    @SneakyThrows
    public void parseObject() {
        var json = "{\"v1\":1,\"v2\":\"test\"\"v3\":[1]}";
        var expected = Map.of(
            "v1", 1,
            "v2", "test",
            "v3", List.of(1)
        );
        var parser = new Parser(json);
        var result = parser.parse();
        MatcherAssert.assertThat(result, Matchers.equalTo(expected));
    }

    @Test
    @SneakyThrows
    public void parseArray() {
        var json = "[1,2]";
        var expected = List.of(1, 2);
        var parser = new Parser(json);
        var result = parser.parse();
        MatcherAssert.assertThat(result, Matchers.equalTo(expected));
    }

    @Test
    @SneakyThrows
    public void parsePrimitive() {
        var json = "1";
        var expected = 1;
        var parser = new Parser(json);
        var result = parser.parse();
        MatcherAssert.assertThat(result, Matchers.equalTo(expected));
    }

}