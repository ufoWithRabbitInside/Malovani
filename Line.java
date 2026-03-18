
public class Line extends Thing{
    public Point point1 = null; 
    public Point point2 = null; 
    public int strenght = 10;
    public boolean isSolid = true;
    public int padding = 5;

    public Line(){
        point1 = new Point(0,0);
        point2 = new Point(0,0);
    };
    public Line(Point point1, Point point2, int color, int strenght, boolean isSolid, int padding){ // strenght za padding nebo isSolid, prohodit
        this.point1 = point1;
        this.point2 = point2;
        this.isSolid = isSolid;
        this.color = color;
        this.padding = padding;
        this.strenght = strenght;

    }
    public int GetHeight(){
        return point2.Y - point1.Y;
    }
    public int GetWidht(){
        return point2.X - point1.X;
    }
    public int GetLength(){
        return (int)Math.sqrt((GetHeight()*GetHeight()) + (GetWidht()*GetWidht()));
    }
    public double GetAngle(){
        return Math.atan2(GetHeight(), GetWidht());
    }

    public Line Copy(){
        return new Line(new Point(point1.X, point1.Y), new Point(point2.X, point2.Y), color, strenght, isSolid, padding);
    }

    public void ZarovnejSe(double testAngleResolution){
        int lenght = GetLength();

        double angle = Math.atan2((double)GetHeight(), (double)GetWidht());
        if(angle > Math.PI)
            angle = angle - Math.PI;
        double lowestError = Double.MAX_VALUE;
        double nearestAngle = 0;
        for(double testAngle = -Math.PI; testAngle <= Math.PI; testAngle += testAngleResolution){
            if(Math.abs(testAngle - angle)<lowestError){
                lowestError = Math.abs(testAngle - angle);
                nearestAngle = testAngle;
            }
        }

        point2.Y = point1.Y + (int)(Math.sin(nearestAngle)*lenght);
        point2.X = point1.X + (int)(Math.cos(nearestAngle)*lenght);

        System.out.println(angle);
    }
}
