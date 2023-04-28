import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BruteForceTest{
    @Test
    void simpleFirstTest(){
        String input = """
                4
                2 2 6 8
                5 4 9 10
                4 0 11 6
                8 2 12 12
                6
                2 2
                12 12
                10 4
                5 5
                2 10
                2 8
                """;
        String expectedOut = "1 0 2 3 0 0 ";
        assertEquals(expectedOut, Writer.getAnswer(input, new BruteForce()));
    }

    @Test
    void simpleSecondTest(){
        String input = """
                2
                2 2 5 5
                5 5 8 8
                7
                2 2
                2 5
                5 2
                5 5
                5 8
                8 5
                8 8
                """;
        String expected = "1 0 0 1 0 0 0 ";
        assertEquals(expected, Writer.getAnswer(input, new BruteForce()));
    }
}