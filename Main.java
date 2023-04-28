import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Writer.write(new Scanner(System.in), new PrintWriter(System.out), new BruteForce());
    }
}

final class Writer {
    public static void write(Scanner in, PrintWriter out, Solver solver){
        List<Rectangle> rectangles = readRectangles(in);
        solver.preprocessData(rectangles);
        List<Point> points = readPoints(in);
        for(Point point:points){
            out.printf("%d ", solver.getPointRectsCount(point));
        }
        out.flush();
    }
    private static List<Point> readPoints(Scanner in) {
        List<Point> points = new ArrayList<>();
        int pointCount = in.nextInt();
        for(int i=0; i<pointCount; i++){
            points.add(Point.readPoint(in));
        }
        return points;
    }

    private static List<Rectangle> readRectangles(Scanner in) {
        List<Rectangle> rectangles = new ArrayList<>();
        int rectCount = in.nextInt();
        for(int i=0; i<rectCount; i++){
            rectangles.add(Rectangle.readRectangle(in));
        }
        return rectangles;
    }
    public static String getAnswer(String input, Solver solver){
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
    public static Point readPoint(Scanner in){
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
            if(point.behaveInRect(rect)){
                count++;
            }
        }
        return count;
    }
}





















