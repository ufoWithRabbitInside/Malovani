
import java.util.ArrayList;
import java.util.List;

public class NAngle extends Thing{
    public List<Point> Points;
    public int strenght;

    public NAngle(Point point, int strenght, int color) {
        Points = new ArrayList<>();
        Points.add(point);
        this.strenght = strenght;
        this.color = color;
    }

    public Point[] CopYpoints(){
        Point[] points = new Point[Points.size()];
        for (int i = 0; i < Points.size(); i++) {
            points[i] = Points.get(i).Copy();
        }
        return points;
    }

    public NAngle(List<Point> points, int strenght, int color){
        Points = new ArrayList<>();
        for (Point point : points) {
            this.Points.add(point.Copy());
        }
        this.strenght = strenght;
        this.color = color;
    }

    public NAngle Copy(){
        return new NAngle(Points, strenght, color);
    }
}
