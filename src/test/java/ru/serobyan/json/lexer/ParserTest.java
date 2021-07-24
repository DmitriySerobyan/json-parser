package ru.serobyan.json.lexer;

import lombok.SneakyThrows;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.serobyan.json.parser.Parser;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class ParserTest {

    @ParameterizedTest
    @MethodSource("cases")
    @SneakyThrows
    public void parse(String json, Object expectedParsed) {
        var parser = new Parser(json);
        var result = parser.parse();
        MatcherAssert.assertThat(result, Matchers.equalTo(expectedParsed));
    }

    private static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(
                "1",
                1
            ),
            Arguments.of(
                "[1,2]",
                List.of(1, 2)
            ),
            Arguments.of(
                "{\"v1\":1,\"v2\":\"test\",\"v3\":[1]}",
                Map.of(
                    "v1", 1,
                    "v2", "test",
                    "v3", List.of(1)
                )
            )
        );
    }

}