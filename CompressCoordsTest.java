import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompressCoordsTest {
    static List<TestStorage.TestData> testsData(){
        return TestStorage.tests;
    }

    @ParameterizedTest
    @MethodSource(value = "testsData")
    void testing(TestStorage.TestData testData){
        assertEquals(testData.out(), Writer.getAnswer(testData.in(), new CompressCoords()));
    }
}