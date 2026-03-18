import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Drawer {
    private final int width;
    private final int height;
    BufferedImage image;

    int back_color = 0;

    public Drawer(int width, int height, int back_color){
        this.width = width;
        this.height = height;
        this.back_color = back_color;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }
    public void setRGB(int x, int y, int c){
        if(x < width && x >= 0 && y < height && y >= 0)
        image.setRGB(x, y, c);
    }
    public void Clear(){

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                image.setRGB(x, y, Color.BLACK.getRGB());
            }
        }
    }
    public void DrawLine(Line line){

        int larger_site;
        if(Math.abs(line.GetHeight()) > Math.abs(line.GetWidht())){
            larger_site = line.GetHeight();
        }
        else{
            larger_site = line.GetWidht();
        }
        float widht_step = (float)line.GetWidht() / (float)larger_site;
        float height_step = (float)line.GetHeight() / (float)larger_site;


        int paint_counter = 0;
        boolean isFilling = true;


        if(larger_site >= 0){
            for(float a = 0; a < larger_site; a++){
                if(isFilling || line.isSolid)
                    setRGB(line.point1.X + (int)(a * widht_step), line.point1.Y + (int)(a*height_step), line.color);
                else
                    setRGB(line.point1.X + (int)(a * widht_step), line.point1.Y + (int)(a*height_step), Color.BLACK.getRGB());

                if(paint_counter > 5){
                    paint_counter = 0;
                    isFilling = !isFilling;
                }
                paint_counter++;
            }
        }
        else{
            for(float a = 0; a > larger_site; a--){
                if(isFilling || line.isSolid)
                    setRGB(line.point1.X + (int)(a * widht_step), line.point1.Y + (int)(a*height_step), line.color);
                else
                    setRGB(line.point1.X + (int)(a * widht_step), line.point1.Y + (int)(a*height_step), Color.BLACK.getRGB());

                if(paint_counter > 5){
                    paint_counter = 0;
                    isFilling = !isFilling;
                }
                paint_counter++;
            }
        }

        //System.out.println("larger site - " + larger_site);
        //System.out.println("widht step - " + widht_step);
        //System.out.println("height step - " + height_step);
        //System.out.println("-----------------------------------");
    }
    public BufferedImage GetImage(){
        return image;
    }
    public void DeleteEverything(Remember remember){
        //Clear();
        //draw line
        for (Line line : remember.Lines) {
            Line black_line = line.Copy();
            black_line.color = back_color;
            Draw(black_line);
        }
        if(remember.previewLine != null){
            Line black_line = remember.previewLine.Copy();
            black_line.color = back_color;
            Draw(black_line);
        }

        //draw circle
        for (Circle circle : remember.Circles) {
            Circle black_circle = circle.Copy();
            black_circle.color = back_color;
            black_circle.fill_color = back_color;
            Draw(black_circle);
        }
        if(remember.previewCircle != null){
            Circle black_circle = remember.previewCircle.Copy();
            black_circle.color = back_color;
            black_circle.fill_color = back_color;
            Draw(black_circle);
        }

        //draw n-angle
        for (NAngle nangle : remember.NAngles) {
            NAngle black_angle = nangle.Copy();
            black_angle.color = back_color;
            black_angle.fill_color = back_color;
            Draw(black_angle);
        }
        if(remember.previewNAngle != null){
            NAngle black_angle = remember.previewNAngle.Copy();
            black_angle.color = back_color;
            black_angle.fill_color = back_color;
            Draw(black_angle);
        }
    }
    public void DrawEverything(Remember remember){
        //Clear();
        //draw line
        for (Line line : remember.Lines) {
            Draw(line);
        }
        if(remember.previewLine != null)
            Draw(remember.previewLine);

        //draw circle
        for (Circle circle : remember.Circles) {
            Draw(circle);
        }
        if(remember.previewCircle != null)
            Draw(remember.previewCircle);

        //draw n-angle
        for (NAngle nangle : remember.NAngles) {
            Draw(nangle);
        }
        if(remember.previewNAngle != null)
            Draw(remember.previewNAngle);


        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < width; x++) {
                setRGB(x, y, Main.color);
            }
        }
    }

    private double Distance(double dx, double dy){
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }
    public double Angle(double dx, double dy){
        return Math.atan2(dy, dx);
    }

    private void Point(int x, int y, int d, int c){
        int r = d/2;
        for (int yp = y-r; yp < y + r; yp++) {
            for (int xp = x-r; xp < x + r; xp++) {
                if(Distance(xp-x, yp-y) <= r){
                    setRGB(xp, yp, c);
                }
            }
        }
    } 
    private void Fill(int x1, int y1, int x2, int y2, int fill_color, int line_color, int back_color){
        boolean isFill;
        int previous_color;

        for (int y = y1; y < y2; y++) {
            isFill = false;
            previous_color = image.getRGB(x1, y);
            for (int x = x1; x < x2; x++) {
                if(previous_color == line_color && image.getRGB(x, y) == back_color)
                    isFill = true;
                if(previous_color == back_color && image.getRGB(x, y) == line_color)
                    isFill = false;
                if(isFill)
                    setRGB(x, y, fill_color);
                previous_color = image.getRGB(x, y);
            }
        }
    }
    
    private void FillPoint(Point point, Fill fill){

        if(image.getRGB(point.X, point.Y) == fill.back_color){
            setRGB(point.X, point.Y, fill.color);
            Random rnd = new Random();
            for (int xlook = point.X-1; xlook <= point.X+1; xlook++) {
                for (int ylook = point.Y-1; ylook <= point.Y+1; ylook++) {
                    
                
                    if(rnd.nextDouble()*(1/Geometry.Distance(point.Y-ylook, point.X-xlook)) > fill.rnd_to_fill && xlook >= 0 && xlook < image.getWidth() && ylook >= 0 && ylook < image.getHeight() && image.getRGB(xlook, ylook) == fill.back_color){
                        fill.new_border.add(new Point(xlook, ylook));
                    }


                }
            }
        }

    }
    public boolean Occupy(Fill fill){
        boolean isnext = false;
        fill.new_border.clear();
        for (int i = 0; i < fill.actual_border.size(); i++) {
            FillPoint(fill.actual_border.get(i).Copy(), fill);
        }
        if(fill.new_border.size() > 0){
            fill.actual_border.clear();
            for (Point new_point : fill.new_border) {
                fill.actual_border.add(new_point);
            }
            isnext = true;
        }
        //if(fill.actual_border.size() > 100000){
        //    isnext = false;
        //}
        return isnext;
    }


    public void Draw(Line line){
        /*
        int line_lenght = line.GetLength();
        double line_angle = line.GetAngle();

        for (int x = -line.strenght/2; x < line.strenght/2; x++) {
            for (int y = 0; y < line_lenght; y++) { //v line.java by to slo zefektivnit, aby se delka porad nedopocitavala jako ted
                setRGB((int)(Distance(x, y)*Math.sin(line_angle+Angle(x, y)) + line.point1.X), (int)(Distance(x, y)*Math.cos(line_angle+Angle(x, y)) + line.point1.Y), line.color);
                
            }
        }
        */
        // trochu neporadek
        int larger_site;
        if(Math.abs(line.GetHeight()) > Math.abs(line.GetWidht())){
            larger_site = line.GetHeight();
        }
        else{
            larger_site = line.GetWidht();
        }
        float widht_step = (float)line.GetWidht() / (float)larger_site;
        float height_step = (float)line.GetHeight() / (float)larger_site;


        int paint_counter = 0;
        boolean isFilling = true;


        if(larger_site >= 0){
            for(float a = 0; a < larger_site; a++){
                if(isFilling || line.isSolid)
                    Point(line.point1.X + (int)(a * widht_step), line.point1.Y + (int)(a*height_step), line.strenght, line.color);
                
                if(paint_counter > line.padding){
                    paint_counter = 0;
                    isFilling = !isFilling;
                }
                paint_counter++;
            }
        }
        else{
            for(float a = 0; a > larger_site; a--){
                if(isFilling || line.isSolid)
                    Point(line.point1.X + (int)(a * widht_step), line.point1.Y + (int)(a*height_step), line.strenght, line.color);
                
                if(paint_counter > line.padding){
                    paint_counter = 0;
                    isFilling = !isFilling;
                }
                paint_counter++;
            }
        }

    }
    public void Draw(Circle circle){
        for (int y = -circle.radius; y < circle.radius; y++) {
            for (int x = -circle.radius; x < circle.radius; x++) {
                if(circle.is_color && Distance(x, y) <= circle.radius && Distance(x, y) >= circle.radius - circle.strenght){
                    setRGB(x + circle.centre.X, y + circle.centre.Y, circle.color);
                }
                if(circle.is_fill_color && Distance(x, y) <= circle.radius - (circle.strenght/2)){
                    setRGB(x + circle.centre.X, y + circle.centre.Y, circle.fill_color);
                }
            }
        }
    }
    public void Draw(NAngle nAngle){
        Point[] points = nAngle.CopYpoints();
        Line line = new Line();
        for (int i = 0; i < points.length; i++) {
            line.point1 = points[i];
            if(i < points.length - 1)
                line.point2 = points[i + 1];
            else
                line.point2 = points[0];

            line.color = nAngle.color;
            line.strenght = nAngle.strenght;
            line.isSolid = true;
            line.padding = 0;

            Draw(line);
        }
    }

    public void Rucicka(Point point, int strenght, int color){
        Point(point.X, point.Y, strenght, color);
    }
}
