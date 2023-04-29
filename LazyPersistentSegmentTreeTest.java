import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class LazyPersistentSegmentTreeTest {
    @Test
    void test() {
        LazyPersistentSegmentTree tree = new LazyPersistentSegmentTree(100);
        int value = 748234;
        int secondValue = 8549;
        tree.add(value, 1, 98);
        tree.add(secondValue, 11, 88);
        assertEquals(0, tree.get(0));
        for (int i = 1; i < 11; i++) {
            assertEquals(value, tree.get(i));
        }
        for (int i = 11; i < 89; i++) {
            assertEquals(secondValue + value, tree.get(i));
        }
        for (int i = 89; i < 99; i++) {
            assertEquals(value, tree.get(i));
        }
        assertEquals(0, tree.get(99));
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 100, 1000, 10000, 100000, 1000000, 10000000})
    void hardTest(int size) {
        LazyPersistentSegmentTree tree = new LazyPersistentSegmentTree(size);
        int value = 10;
        int shift = 7;
        tree.add(value, 1, size - 2);
        assertEquals(0, tree.get(0));
        assertEquals(0, tree.get(size-1));
        for (int i = 1; i<size-1; i++){
            if(i%shift==0){
                assertEquals(value, tree.get(i));
            }
        }
    }
}