import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class LazyPersistentSegmentTreeTest {
    @Test
    void calcDepth() {
        LazyPersistentSegmentTree tree = new LazyPersistentSegmentTree(5);
        assertEquals(3, tree.getDepth());
        tree = new LazyPersistentSegmentTree(4);
        assertEquals(2, tree.getDepth());
        tree = new LazyPersistentSegmentTree(8);
        assertEquals(3, tree.getDepth());
    }

    @Test
    void verySimpleTest() {
        LazyPersistentSegmentTree tree = new LazyPersistentSegmentTree(4);
        int value = 2412;
        LazyPersistentSegmentTree newVersionTree = tree.add(value, 1, 2);
        assertEquals(0, newVersionTree.get(0));
        IntStream.range(1, 3).forEach(i -> assertEquals(value, newVersionTree.get(i)));
        assertEquals(0, newVersionTree.get(3));
    }

    @Test
    void simpleTest() {
        LazyPersistentSegmentTree tree = new LazyPersistentSegmentTree(100);
        int value = 2412;
        LazyPersistentSegmentTree newVersionTree = tree.add(value, 1, 98);
        assertEquals(0, newVersionTree.get(0));
        IntStream.range(1, 99).forEach(i -> assertEquals(value, newVersionTree.get(i)));
        assertEquals(0, newVersionTree.get(99));
    }

    @Test
    void muchTreeVersionSimpleTest(){
        LazyPersistentSegmentTree tree = new LazyPersistentSegmentTree(10);
        int value = 123;
        int value2 = 596;
        LazyPersistentSegmentTree tree1 = tree.add(value, 0, 5);
        IntStream.range(0, 6).forEach(i -> assertEquals(value, tree1.get(i)));
        LazyPersistentSegmentTree tree2 = tree.add(value2, 5, 9);
        IntStream.range(5, 10).forEach(i -> assertEquals(value2, tree2.get(i)));
        LazyPersistentSegmentTree tree3 = tree1.add(value2, 3, 9);
        IntStream.range(0, 3).forEach(i -> assertEquals(value, tree3.get(i), "Tree3.get(%d)=%d!".formatted(i, tree3.get(i))));
        IntStream.range(3, 6).forEach(i -> assertEquals(value+value2, tree3.get(i)));
        IntStream.range(6, 10).forEach(i -> assertEquals(value2, tree3.get(i)));
    }

    @Test
    void veryMuchTreeVersion(){
        LazyPersistentSegmentTree tree = new LazyPersistentSegmentTree(100);
        int value = 123;
        int value2 = 596;
        LazyPersistentSegmentTree tree1 = tree.add(value, 0, 50);
        IntStream.range(0, 51).forEach(i -> assertEquals(value, tree1.get(i)));
        LazyPersistentSegmentTree tree2 = tree.add(value2, 50, 99);
        IntStream.range(50, 100).forEach(i -> assertEquals(value2, tree2.get(i)));
        LazyPersistentSegmentTree tree3 = tree1.add(value2, 25, 99);
        IntStream.range(0, 25).forEach(i -> assertEquals(value, tree3.get(i), "Tree3.get(%d)=%d!".formatted(i, tree3.get(i))));
        IntStream.range(25, 51).forEach(i -> assertEquals(value+value2, tree3.get(i)));
        IntStream.range(51, 100).forEach(i -> assertEquals(value2, tree3.get(i)));
    }

    @Test
    void veryMuchTreeVersionTest(){
        LazyPersistentSegmentTree tree = new LazyPersistentSegmentTree(100);
        int value = 123;
        int value2 = 596;
        LazyPersistentSegmentTree tree1 = tree.add(value, 0, 50);
        LazyPersistentSegmentTree tree3 = tree1.add(value2, 25, 99);
        IntStream.range(0, 25).forEach(i -> assertEquals(value, tree3.get(i), "Tree3.get(%d)=%d!".formatted(i, tree3.get(i))));
        IntStream.range(25, 51).forEach(i -> assertEquals(value+value2, tree3.get(i)));
        IntStream.range(51, 100).forEach(i -> assertEquals(value2, tree3.get(i)));
    }

    @Test
    void versionsTest(){
        LazyPersistentSegmentTree tree = new LazyPersistentSegmentTree(10);
        int value1 = 123;
        int value2 = 596;
        int value3 = 2458;
        LazyPersistentSegmentTree tree1 = tree.add(value1, 0, 5);
        LazyPersistentSegmentTree tree2 = tree1.add(value2, 0, 9);
        LazyPersistentSegmentTree tree3 = tree2.add(value3, 3, 9);

        IntStream.range(0, 6).forEach(i -> assertEquals(value1, tree1.get(i)));
        IntStream.range(6, 10).forEach(i -> assertEquals(0, tree1.get(i)));

        IntStream.range(0, 6).forEach(i -> assertEquals(value1+value2, tree2.get(i)));
        IntStream.range(6, 10).forEach(i -> assertEquals(value2, tree2.get(i)));

        IntStream.range(0, 3).forEach(i -> assertEquals(value1+value2, tree3.get(i)));
        IntStream.range(3, 6).forEach(i -> assertEquals(value1+value2+value3, tree3.get(i)));
        IntStream.range(6, 10).forEach(i -> assertEquals(value2+value3, tree3.get(i)));
    }

    @Test
    void fourVersionTest(){
        LazyPersistentSegmentTree tree = new LazyPersistentSegmentTree(4);
        LazyPersistentSegmentTree tree1 = tree.add(1, 0, 0);
        IntStream.range(0, 1).forEach(i -> assertEquals(1, tree1.get(i)));
        IntStream.range(1, 4).forEach(i -> assertEquals(0, tree1.get(i)));

        LazyPersistentSegmentTree tree2 = tree1.add(1, 0, 1);
        IntStream.range(0, 1).forEach(i -> assertEquals(2, tree2.get(i)));
        IntStream.range(1, 2).forEach(i -> assertEquals(1, tree2.get(i)));
        IntStream.range(2, 4).forEach(i -> assertEquals(0, tree2.get(i)));

        LazyPersistentSegmentTree tree3 = tree2.add(1, 0, 2);
        IntStream.range(0, 1).forEach(i -> assertEquals(3, tree3.get(i)));
        IntStream.range(1, 2).forEach(i -> assertEquals(2, tree3.get(i)));
        IntStream.range(2, 3).forEach(i -> assertEquals(1, tree3.get(i)));
        IntStream.range(3, 4).forEach(i -> assertEquals(0, tree3.get(i)));

        LazyPersistentSegmentTree tree4 = tree3.add(1, 0, 3);
        IntStream.range(0, 1).forEach(i -> assertEquals(4, tree4.get(i)));
        IntStream.range(1, 2).forEach(i -> assertEquals(3, tree4.get(i)));
        IntStream.range(2, 3).forEach(i -> assertEquals(2, tree4.get(i)));
        IntStream.range(3, 4).forEach(i -> assertEquals(1, tree4.get(i)));
    }

    @Test
    void fourVersionSimpleTest(){
        LazyPersistentSegmentTree tree = new LazyPersistentSegmentTree(4);
        LazyPersistentSegmentTree tree4 = tree.add(1, 0, 0)
                .add(1, 0, 1)
                .add(1, 0, 2)
                .add(1, 0, 3);
        IntStream.range(0, 1).forEach(i -> assertEquals(4, tree4.get(i)));
        IntStream.range(1, 2).forEach(i -> assertEquals(3, tree4.get(i)));
        IntStream.range(2, 3).forEach(i -> assertEquals(2, tree4.get(i)));
        IntStream.range(3, 4).forEach(i -> assertEquals(1, tree4.get(i)));
    }
}