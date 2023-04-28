import java.util.ArrayList;
import java.util.List;

public final class TestStorage {
    public record TestData(String in, String out){}
    public final static List<TestData> tests = new ArrayList<>(
            List.of(
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
                            """, "1 0 0 1 0 0 0 ")
            )
    );
}
