import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class MainTest {
    final static int pointCount = 1342;

    public static void main(String[] args) {
        for (int N = 1; N < Math.pow(10, 3); N+=7) {
            String input = prepareData(N);
            SolverTimeAndAnswer bruteForceTime = getTime(input, new BruteForce());
            SolverTimeAndAnswer compressCoordsTime = getTime(input, new CompressCoords());
            SolverTimeAndAnswer lazyPSTreeTime = getTime(input, new LazySegmentTreeSolver());
            if (!equalsArgs(
                    bruteForceTime.answer,
                    compressCoordsTime.answer,
                    lazyPSTreeTime.answer))
                throw new RuntimeException("Answers is not equals!");
            System.out.printf("%d " +
                            "%d %d " +
                            "%d %d " +
                            "%d %d\n", N,
                    bruteForceTime.preprocessTime, bruteForceTime.queryTime,
                    compressCoordsTime.preprocessTime, compressCoordsTime.queryTime,
                    lazyPSTreeTime.preprocessTime, lazyPSTreeTime.queryTime);
        }
    }

    private record SolverTimeAndAnswer(long preprocessTime, long queryTime, String answer) {
    }

    private static SolverTimeAndAnswer getTime(String input, Solver solver) {
        Scanner in = new Scanner(input);

        long startPreprocess = System.nanoTime();
        solver.preprocessData(readRectangles(in));
        long endPreprocess = System.nanoTime();

        StringBuilder answer = new StringBuilder();
        long startQuery = System.nanoTime();
        readPoints(in).forEach(point -> answer.append(solver.getPointRectsCount(point)));
        long endQuery = System.nanoTime();

        return new SolverTimeAndAnswer(endPreprocess - startPreprocess, (endQuery - startQuery) / pointCount, answer.toString());
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

    private static boolean equalsArgs(Object... args) {
        Object pred = null;
        for (Object obj : args) {
            if (pred != null) {
                if (!obj.equals(pred)) return false;
            }
            pred = obj;
        }
        return true;
    }

    private static String prepareData(int N) {
        StringBuilder input = new StringBuilder("%d\n".formatted(N));
        for (int i = 0; i < N; i++) {
            input.append("%d %d %d %d\n".formatted(10 * i, 10 * i, (10 * (2 * N - i)), (10 * (2 * N - i))));
        }
        input.append("%d\n".formatted(pointCount));
        for (int i = 0; i < pointCount; i++) {
            input.append("%d %d\n".formatted(randInt(0, 20 * N), randInt(0, 20 * N)));
        }
        return input.toString();
    }

    private static int randInt(int min, int max) {
        return (int) (min + Math.random() * (max - min));
    }
}