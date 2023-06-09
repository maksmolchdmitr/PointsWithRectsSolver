import java.util.ArrayList;
import java.util.List;

public final class TestStorage {
    public record TestData(String in, String out){
        public static TestData getTest(String in){
            return new TestData(in, Writer.getAnswer(in, new BruteForce()));
        }
    }
    public final static List<TestData> tests = new ArrayList<>(
            List.of(
                    new TestData("""
                            4
                            0 0 2 2
                            0 0 4 4
                            0 0 6 6
                            0 0 8 8
                            2
                            0 0
                            2 2
                            """, "4 3 "),
                    new TestData("""
                            2
                            2 2 5 5
                            5 5 8 8
                            3
                            2 2
                            5 5
                            8 8
                            """, "1 1 0 "),
                    new TestData("""
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
                            """, "1 0 0 1 0 0 0 "),
                    new TestData("""
                            2
                            2 1 7 10
                            4 4 10 8
                            1
                            9 7
                            """, "1 "),
                    new TestData("""
                            2
                            2 1 7 10
                            4 4 10 8
                            7
                            2 1
                            4 3
                            4 4
                            5 5
                            7 8
                            9 7
                            6 10
                            """, "1 1 2 2 0 1 0 "),
                    new TestData("""
                            2
                            2 1 4 4
                            3 2 6 6
                            5
                            2 1
                            3 2
                            4 3
                            3 3
                            4 1
                            """, "1 2 1 2 0 "),
                    new TestData("""
                            1
                            1 1 5 5
                            4
                            1 1
                            1 5
                            5 1
                            5 5
                            """, "1 0 0 0 "),
                    new TestData("""
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
                            """, "1 0 2 3 0 0 "),
                    new TestData("""
                            2
                            5 5 10 10
                            7 7 18 18
                            1
                            1 1
                            """, "0 "),
                    TestData.getTest("""
                            2
                            2 2 5 5
                            5 6 8 8
                            8
                            2 2
                            5 5
                            5 2
                            2 5
                            5 6
                            8 8
                            8 6
                            5 8
                            """),
                    new TestData("""
                            2
                            2 2 5 5
                            5 6 8 8
                            8
                            2 2
                            5 5
                            5 2
                            2 5
                            5 6
                            8 8
                            8 6
                            5 8
                            """, "1 0 0 0 1 0 0 0 "),
                    new TestData("""
                            4
                            2 2 6 8
                            5 4 9 10
                            4 0 11 6
                            8 2 12 12
                            5
                            2 2
                            12 12
                            10 4
                            5 5
                            2 10
                            """, "1 0 2 3 0 ")
            )
    );
}
