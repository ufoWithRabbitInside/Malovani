public class Circle extends Thing{
    public Point centre = null;
    public int radius = 0;
    public int strenght = 1;
    public Circle(Point centre, int radius, int strenght, int color){
        this.centre = centre;
        this.radius = radius;
        this.strenght = strenght;
        this.color = color;
    }
    public Circle(){
    }
    public Circle Copy(){
        return new Circle(centre.Copy(), radius, strenght, color);
    }
}
