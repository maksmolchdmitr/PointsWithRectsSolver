import lombok.Getter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Writer.write(new Scanner(System.in), new PrintWriter(System.out), new CompressCoords());
    }
}

final class Writer {
    public static void write(Scanner in, PrintWriter out, Solver solver) {
        List<Rectangle> rectangles = readRectangles(in);
        solver.preprocessData(rectangles);
        List<Point> points = readPoints(in);
        for (Point point : points) {
            out.printf("%d ", solver.getPointRectsCount(point));
        }
        out.flush();
    }

    private static List<Point> readPoints(Scanner in) {
        List<Point> points = new ArrayList<>();
        int pointCount = in.nextInt();
        for (int i = 0; i < pointCount; i++) {
            points.add(Point.readPoint(in));
        }
        return points;
    }

    private static List<Rectangle> readRectangles(Scanner in) {
        List<Rectangle> rectangles = new ArrayList<>();
        int rectCount = in.nextInt();
        for (int i = 0; i < rectCount; i++) {
            rectangles.add(Rectangle.readRectangle(in));
        }
        return rectangles;
    }

    public static String getAnswer(String input, Solver solver) {
        StringWriter output = new StringWriter();
        write(new Scanner(input), new PrintWriter(output), solver);
        return output.toString();
    }
}

record Point(int x, int y) {
    public boolean behaveInRect(Rectangle rect) {
        return (x >= rect.leftDown().x && y >= rect.leftDown().y)
                && (x < rect.rightUp().x && y < rect.rightUp().y);
    }

    public static Point readPoint(Scanner in) {
        return new Point(in.nextInt(), in.nextInt());
    }
}

record Rectangle(Point leftDown, Point rightUp) {
    public static Rectangle readRectangle(Scanner in) {
        return new Rectangle(
                Point.readPoint(in),
                Point.readPoint(in)
        );
    }
}

interface Solver {
    void preprocessData(List<Rectangle> rects);

    int getPointRectsCount(Point point);
}

// Brute force solver

final class BruteForce implements Solver {
    private List<Rectangle> rects;

    @Override
    public void preprocessData(List<Rectangle> rects) {
        this.rects = rects;
    }

    @Override
    public int getPointRectsCount(Point point) {
        int count = 0;
        for (Rectangle rect : rects) {
            if (point.behaveInRect(rect)) {
                count++;
            }
        }
        return count;
    }
}

// Coordinate compressing solver

final class CompressCoords implements Solver {
    private List<Integer> coordsX = new ArrayList<>(), coordsY = new ArrayList<>();
    private int[][] map;

    @Override
    public void preprocessData(List<Rectangle> rects) {
        if (rects.size() == 0) return;
        CoordsCompressor.compress(rects, coordsX, coordsY);
        coordsX = CoordsCompressor.removeUniqueValues(coordsX);
        coordsY = CoordsCompressor.removeUniqueValues(coordsY);
        map = new int[coordsX.size() - 1][coordsY.size() - 1];
        for (Rectangle rect : rects) {
            Point mappedLeftDown = findMappedPoint(rect.leftDown()).orElseThrow();
            Point mappedRightUp = findMappedPoint(downPoint(rect.rightUp())).orElseThrow();
            for (int mappedX = mappedLeftDown.x(); mappedX <= mappedRightUp.x(); mappedX++) {
                for (int mappedY = mappedLeftDown.y(); mappedY <= mappedRightUp.y(); mappedY++) {
                    map[mappedX][mappedY]++;
                }
            }
        }
    }

    private Point downPoint(Point point) {
        return new Point(point.x() - 1, point.y() - 1);
    }

    @Override
    public int getPointRectsCount(Point point) {
        Optional<Point> optionalPoint = findMappedPoint(point);
        if (optionalPoint.isEmpty()) return 0;
        Point mappedPoint = optionalPoint.get();
        return map[mappedPoint.x()][mappedPoint.y()];
    }

    private Optional<Point> findMappedPoint(Point point) {
        int x = findMappedIndex(coordsX, point.x());
        int y = findMappedIndex(coordsY, point.y());
        if (x == -1 || y == -1) return Optional.empty();
        return Optional.of(new Point(x, y));
    }

    private static int findMappedIndex(List<Integer> coords, int value) {
        if (value == coords.get(0)) return 0;
        if (value < coords.get(0) ||
                value > coords.get(coords.size() - 1) ||
                value == coords.get(coords.size() - 1)) return -1;
        if (value < coords.get(1)) return 0;
        return binaryFind(0, coords.size() - 1, value, coords);
    }

    private static int binaryFind(int l, int r, final Integer value, final List<Integer> arr) {
        if (r - l <= 1) {
            if (value.equals(arr.get(r))) return r;
            if (value.equals(arr.get(l))) return l;
            return l;
        }
        int middle = (l + r) / 2;
        if (value > arr.get(middle)) {
            return binaryFind(middle, r, value, arr);
        }
        if (value < arr.get(middle)) {
            return binaryFind(l, middle, value, arr);
        }
        return middle;
    }
}

final class CoordsCompressor {
    public static void compress(List<Rectangle> rects, List<Integer> coordsX, List<Integer> coordsY) {
        for (Rectangle rect : rects) {
            coordsX.add(rect.rightUp().x());
            coordsX.add(rect.leftDown().x());
            coordsY.add(rect.leftDown().y());
            coordsY.add(rect.rightUp().y());
        }
        Collections.sort(coordsX);
        Collections.sort(coordsY);
    }

    public static List<Integer> removeUniqueValues(List<Integer> list) {
        List<Integer> res = new ArrayList<>(list.size());
        Integer prev = null;
        for (Integer value : list) {
            if (prev == null || !prev.equals(value)) {
                res.add(value);
            }
            prev = value;
        }
        return res;
    }
}

class LazyPersistentSegmentTree {
    private final Node root;

    @Getter
    public static class Node {
        private int modify;

        public Node() {
            this.modify = 0;
            this.right = null;
            this.left = null;
        }

        public Node(int modify, Node left, Node right) {
            this.modify = modify;
            this.left = left;
            this.right = right;
        }

        private Node left, right;

        public Node clone() {
            return new Node(this.modify, this.left, this.right);
        }

        public Node(int modify) {
            this.modify = modify;
        }
    }

    private final int depth;

    public int getDepth() {
        return depth;
    }

    public LazyPersistentSegmentTree(int length) {
        this.depth = (int) Math.ceil(Math.log(length) / Math.log(2));
        this.root = new Node(0, null, null);
    }

    private LazyPersistentSegmentTree(Node root, int depth) {
        this.root = root;
        this.depth = depth;
    }

    public int get(int i) {
        return get(this.root, i, 0, (int) (Math.pow(2, depth) - 1));
    }

    private int get(Node node, Integer index, int lNode, int rNode) {
        if (lNode == rNode && lNode == index) {
            return node.modify;
        }
        int middle = (lNode + rNode) / 2;
        int inheritance = node.modify;
        if (node.left == null) {
            node.left = new Node(inheritance);
        } else {
            node.left.modify += inheritance;
        }
        if (node.right == null) {
            node.right = new Node(inheritance);
        } else {
            node.right.modify += inheritance;
        }
        node.modify = 0;
        if (index <= middle) {
            return get(node.left, index, lNode, middle);
        } else {
            return get(node.right, index, middle + 1, rNode);
        }
    }

    public LazyPersistentSegmentTree add(int value, int l, int r) {
        LazyPersistentSegmentTree newTree = new LazyPersistentSegmentTree(this.root.clone(), depth);
        add(newTree.root, value, l, r, 0, (int) (Math.pow(2, depth) - 1));
        return newTree;
    }

    private void add(Node node, final Integer value, final Integer l, final Integer r, int lNode, int rNode) {
        if (l <= lNode && r >= rNode) {
            node.modify += value;
            return;
        }
        int middle = (lNode + rNode) / 2;
        if (r > middle && l <= middle) {
            if (node.left == null) {
                node.left = new Node();
            } else {
                node.left = node.left.clone();
            }
            if (node.right == null) {
                node.right = new Node();
            } else {
                node.right = node.right.clone();
            }
            add(node.left, value, l, r, lNode, middle);
            add(node.right, value, l, r, middle + 1, rNode);
        }
        if (r <= middle) {
            if (node.left == null) {
                node.left = new Node();
            } else {
                node.left = node.left.clone();
            }
            add(node.left, value, l, r, lNode, middle);
        }
        if (l > middle) {
            if (node.right == null) {
                node.right = new Node();
            } else {
                node.right = node.right.clone();
            }
            add(node.right, value, l, r, middle + 1, rNode);
        }
    }
}













