public class Point{
    public int X = 0;
    public int Y = 0;
    public Point(int X, int Y){
        this.X = X;
        this.Y = Y;
    }
    public Point Copy(){
        return new Point(X, Y);
    }
}
